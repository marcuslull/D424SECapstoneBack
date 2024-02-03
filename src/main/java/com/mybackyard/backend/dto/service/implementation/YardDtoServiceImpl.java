package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.YardDtoMapper;
import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.dto.service.interfaces.YardDtoService;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YardDtoServiceImpl implements YardDtoService {

    private final YardDtoMapper yardDtoMapper;
    private final DtoValidator dtoValidator;

    public YardDtoServiceImpl(YardDtoMapper yardDtoMapper, DtoValidator dtoValidator) {
        this.yardDtoMapper = yardDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<YardDto> processOutgoingYardList(List<Yard> yardList) {
        return yardDtoMapper.yardsToDtos(yardList);
    }

    @Override
    public YardDto processOutgoingYard(Yard yard) {
        return yardDtoMapper.yardToDto(yard);
    }

    @Override
    public Yard processIncomingYardDto(YardDto yardDto, boolean isFromPatch) {
        if (!dtoValidator.isWellFormed(yardDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return yardDtoMapper.dtoToYard(yardDto, isFromPatch);
    }

    @Override
    public YardDto addId(YardDto yardDto, long yardId) {
        return new YardDto(yardId, yardDto.name(), yardDto.hardinessZone(), yardDto.soilType(), yardDto.sunExposure(),
                yardDto.yardSubType(), yardDto.noteIds(), yardDto.animalIds(), yardDto.plantIds(), yardDto.userId());
    }
}
