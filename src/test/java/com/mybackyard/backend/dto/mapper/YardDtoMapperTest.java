package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.model.User;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mybackyard.backend.model.enums.HardinessZone.ZONE_6;
import static com.mybackyard.backend.model.enums.SoilType.SILTY;
import static com.mybackyard.backend.model.enums.SunExposure.PARTIAL_SHADE;
import static com.mybackyard.backend.model.enums.YardSubType.FRONT_YARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YardDtoMapperTest {

    private UserService userService;
    private YardDtoMapper yardDtoMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        yardDtoMapper = new YardDtoMapper(userService);
    }

    @Test
    void yardsToDtos() {
        YardDto yardDto = new YardDto(1, "name", "ZONE_6","SILTY", "PARTIAL_SHADE", "FRONT_YARD", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        List<YardDto> yardDtoList = List.of(yardDto);
        Yard yard = new Yard();
        yard.setYardId(1);
        yard.setName("name");
        yard.setHardinessZone(ZONE_6);
        yard.setSoilType(SILTY);
        yard.setSunExposure(PARTIAL_SHADE);
        yard.setYardSubType(FRONT_YARD);
        yard.setNotes(new ArrayList<>());
        yard.setPlants(new ArrayList<>());
        yard.setAnimals(new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        yard.setYUserId(1);
        yard.setUser(user);
        List<Yard> yardList = List.of(yard);

        assertEquals(yardDtoList, yardDtoMapper.yardsToDtos(yardList));
    }

    @Test
    void yardToDto() {
        YardDto yardDto = new YardDto(1, "name", "ZONE_6","SILTY", "PARTIAL_SHADE", "FRONT_YARD", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        Yard yard = new Yard();
        yard.setYardId(1);
        yard.setName("name");
        yard.setHardinessZone(ZONE_6);
        yard.setSoilType(SILTY);
        yard.setSunExposure(PARTIAL_SHADE);
        yard.setYardSubType(FRONT_YARD);
        yard.setNotes(new ArrayList<>());
        yard.setPlants(new ArrayList<>());
        yard.setAnimals(new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        yard.setYUserId(1);
        yard.setUser(user);

        assertEquals(yardDto, yardDtoMapper.yardToDto(yard));
    }

    @Test
    void dtoToYard() {
        YardDto yardDto = new YardDto(1, "name", "ZONE_6","SILTY", "PARTIAL_SHADE", "FRONT_YARD", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        Yard yard = new Yard();
        yard.setYardId(1);
        yard.setName("name");
        yard.setHardinessZone(ZONE_6);
        yard.setSoilType(SILTY);
        yard.setSunExposure(PARTIAL_SHADE);
        yard.setYardSubType(FRONT_YARD);
        yard.setNotes(new ArrayList<>());
        yard.setPlants(new ArrayList<>());
        yard.setAnimals(new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        yard.setYUserId(1);
        yard.setUser(user);
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        assertEquals(yard.getName(), yardDtoMapper.dtoToYard(yardDto, true).getName());
        assertEquals(yard.getName(), yardDtoMapper.dtoToYard(yardDto, false).getName());
    }
}