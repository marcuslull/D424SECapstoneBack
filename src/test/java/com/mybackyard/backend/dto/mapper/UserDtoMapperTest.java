package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoMapperTest {
    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = new UserDtoMapper();
    }

    @Test
    void usersToDtos() {
        UserDto userDto = new UserDto(1,"first","last", "email@email.com", "apiKey", new ArrayList<>());
        List<UserDto> userDtoList = List.of(userDto);
        User user = new User();
        user.setUserId(1);
        user.setFirst("first");
        user.setLast("last");
        user.setEmail("email@email.com");
        user.setApiKey("apiKey");
        user.setYards(new ArrayList<>());
        List<User> userList = List.of(user);

        assertEquals(userDtoList, userDtoMapper.usersToDtos(userList));
    }

    @Test
    void userToDto() {
        UserDto userDto = new UserDto(1,"first","last", "email@email.com", "apiKey", new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        user.setFirst("first");
        user.setLast("last");
        user.setEmail("email@email.com");
        user.setApiKey("apiKey");
        user.setYards(new ArrayList<>());

        assertEquals(userDto, userDtoMapper.userToDto(user));
    }

    @Test
    void dtoToUser() {
        UserDto userDto = new UserDto(1,"first","last", "email@email.com", "apiKey", new ArrayList<>());
        User user = new User();
        user.setUserId(1);
        user.setFirst("first");
        user.setLast("last");
        user.setEmail("email@email.com");
        user.setApiKey("apiKey");
        user.setYards(new ArrayList<>());

        assertEquals(user.getFirst(), userDtoMapper.dtoToUser(userDto, true).getFirst());
        assertEquals(user.getFirst(), userDtoMapper.dtoToUser(userDto, false).getFirst());
    }
}