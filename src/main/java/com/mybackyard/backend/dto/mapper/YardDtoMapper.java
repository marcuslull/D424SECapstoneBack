package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.model.enums.HardinessZone;
import com.mybackyard.backend.model.enums.SoilType;
import com.mybackyard.backend.model.enums.SunExposure;
import com.mybackyard.backend.model.enums.YardSubType;
import com.mybackyard.backend.service.interfaces.UserService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class YardDtoMapper {

    private final UserService userService;

    public YardDtoMapper(UserService userService) {
        this.userService = userService;
    }


    public List<YardDto> yardsToDtos(List<Yard> yardList) {
        List<YardDto> yardDtos = new ArrayList<>();
        for (Yard yard : yardList) {
            yardDtos.add(convertYardToDto(yard));
        }
        return yardDtos;
    }

    public YardDto yardToDto(Yard yard) {
        return convertYardToDto(yard);
    }

    public Yard dtoToYard(YardDto yardDto, Boolean isFromPatch) {
        return convertDtoToYard(yardDto, isFromPatch);
    }

    private Yard convertDtoToYard(YardDto yardDto, Boolean isFromPatch) {
        // this could be from a POST or PATCH request so, we need to maintain the empty fields while converting

        Yard yard = new Yard();

        if (isFromPatch) {
            for (Field field : yardDto.getClass().getDeclaredFields()) {
                field.setAccessible(true); // not sure if this is needed
                try {
                    switch (field.getName()) {
                        case "name" -> {
                            if (field.get(yardDto) != null) yard.setName((String) field.get(yardDto));
                        }
                        case "hardinessZone" -> {
                            if (field.get(yardDto) != null)
                                yard.setHardinessZone(HardinessZone.valueOf((String) field.get(yardDto)));
                        }
                        case "soilType" -> {
                            if (field.get(yardDto) != null)
                                yard.setSoilType(SoilType.valueOf((String) field.get(yardDto)));
                        }
                        case "sunExposure" -> {
                            if (field.get(yardDto) != null)
                                yard.setSunExposure(SunExposure.valueOf((String) field.get(yardDto)));
                        }
                        case "yardSubType" -> {
                            if (field.get(yardDto) != null)
                                yard.setYardSubType(YardSubType.valueOf((String) field.get(yardDto)));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return yard;
        }

        yard.setName(yardDto.name());
        yard.setHardinessZone(HardinessZone.valueOf(yardDto.hardinessZone()));
        yard.setSoilType(SoilType.valueOf(yardDto.soilType()));
        yard.setSunExposure(SunExposure.valueOf(yardDto.sunExposure()));
        yard.setYardSubType(YardSubType.valueOf(yardDto.yardSubType()));
        yard.setNotes(new ArrayList<>());
        yard.setAnimals(new ArrayList<>());
        yard.setPlants(new ArrayList<>());
        yard.setUser(userService.getUserById(yardDto.userId())
                .orElseThrow(() -> new NoSuchElementException("User id associated with yard does not exist")));

        return yard;
    }

    private YardDto convertYardToDto(Yard yard) {

        ArrayList<Long> noteIds = new ArrayList<>();
        yard.getNotes().forEach(note -> noteIds.add(note.getNoteId()));

        ArrayList<Long> animalIds = new ArrayList<>();
        yard.getAnimals().forEach(animal -> animalIds.add(animal.getAnimalId()));

        ArrayList<Long> plantIds = new ArrayList<>();
        yard.getPlants().forEach(plant -> plantIds.add(plant.getPlantId()));

        return new YardDto(
                yard.getYardId(),
                yard.getName(),
                Optional.ofNullable(yard.getHardinessZone()).map(Enum::toString).orElse(""),
                Optional.ofNullable(yard.getSoilType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(yard.getSunExposure()).map(Enum::toString).orElse(""),
                Optional.ofNullable(yard.getYardSubType()).map(Enum::toString).orElse(""),
                noteIds,
                animalIds,
                plantIds,
                yard.getUser().getUserId()
        );
    }
}
























