package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.YardDtoMapper;
import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.dto.service.interfaces.YardDtoService;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YardDtoServiceImplTest {

    private YardDtoMapper yardDtoMapper;
    private DtoValidator dtoValidator;
    private YardDtoService yardDtoService;

    @BeforeEach
    void setUp() {
        yardDtoMapper = mock(YardDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        yardDtoService = new YardDtoServiceImpl(yardDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingYardList() {
    }

    @Test
    void processOutgoingYard() {
    }

    @Test
    void processIncomingYardDto() {
        YardDto yardDto = new YardDto(1,"name", null, null, null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        YardDto badYardDto = new YardDto(1,"name", null, "INVALID", null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        Yard yard = new Yard();
        yard.setYardId(1);
        yard.setName("name");
        yard.setYUserId(1);

        when(dtoValidator.isWellFormed(yardDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badYardDto)).thenReturn(false);
        when(yardDtoMapper.dtoToYard(yardDto, false)).thenReturn(yard);

        assertEquals(yard, yardDtoService.processIncomingYardDto(yardDto, false));
        assertThrows(RuntimeException.class, () -> yardDtoService.processIncomingYardDto(badYardDto, false));
    }

    @Test
    void addId() {
        YardDto yardDto = new YardDto(1,"name", null, null, null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        YardDto baseYardDto = new YardDto(0,"name", null, null, null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        long yardId = 1;
        assertEquals(yardDto, yardDtoService.addId(baseYardDto, yardId));
    }
}