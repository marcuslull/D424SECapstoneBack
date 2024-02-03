package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.UserDtoMapper;
import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.dto.service.interfaces.UserDtoService;
import com.mybackyard.backend.model.User;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDtoServiceImplTest {

    private UserDtoMapper userDtoMapper;
    private DtoValidator dtoValidator;
    private UserDtoService userDtoService;

    @BeforeEach
    void setUp() {
        userDtoMapper = mock(UserDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        userDtoService = new UserDtoServiceImpl(userDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingUserList() {
    }

    @Test
    void processOutgoingUser() {
    }

    @Test
    void processIncomingUserDto() {
        UserDto userDto = new UserDto(1,"first", "last", "email@email.com", "password", new ArrayList<>());
        UserDto badUserDto = new UserDto(1,"name", null, "email@email.com", "password", new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        user.setFirst("first");
        user.setLast("last");
        user.setEmail("email@email.com");
        user.setApiKey("password");

        when(dtoValidator.isWellFormed(userDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badUserDto)).thenReturn(false);
        when(userDtoMapper.dtoToUser(userDto, false)).thenReturn(user);

        assertEquals(user, userDtoService.processIncomingUserDto(userDto, false));
        assertThrows(RuntimeException.class, () -> userDtoService.processIncomingUserDto(badUserDto, false));
    }

    @Test
    void addId() {
        UserDto userDto = new UserDto(1,"name", "last", "email@email.com", "password", new ArrayList<>());
        UserDto baseUserDto = new UserDto(0,"name", "last", "email@email.com", "password", new ArrayList<>());
        long userId = 1;
        assertEquals(userDto, userDtoService.addId(baseUserDto, userId));
    }
}