package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.model.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoMapper {

    public List<UserDto> usersToDtos(List<User> userList) {

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userList) {
            userDtos.add(convertUserToDto(user));
        }
        return userDtos;
    }

    public UserDto userToDto(User user) {
        return convertUserToDto(user);
    }

    public User dtoToUser(UserDto userDto, Boolean isFromPatch) {
        return convertDtoToUser(userDto, isFromPatch);
    }

    private User convertDtoToUser(UserDto userDto, Boolean isFromPatch) {
        // this could be from a POST or PATCH request so, we need to maintain the empty fields while converting

        User user = new User();

        if (isFromPatch) {
            for (Field field : userDto.getClass().getDeclaredFields()) {
                field.setAccessible(true); // not sure if this is needed
                try {
                    switch (field.getName()) {
                        case "first" -> {
                            if (field.get(userDto) != null) user.setFirst((String) field.get(userDto));
                        }
                        case "last" -> {
                            if (field.get(userDto) != null) user.setLast((String) field.get(userDto));
                        }
                        case "email" -> {
                            if (field.get(userDto) != null) user.setEmail((String) field.get(userDto));
                        }
                        case "apiKey" -> {
                            if (field.get(userDto) != null) user.setApiKey((String) field.get(userDto));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return user;
        }

        user.setFirst(userDto.first());
        user.setLast(userDto.last());
        user.setEmail(userDto.email());
        user.setApiKey(userDto.apiKey());

        return user;
    }

    private UserDto convertUserToDto(User user) {

        ArrayList<Long> yardIds = new ArrayList<>();
        user.getYards().forEach(yard -> yardIds.add(yard.getYardId()));

        return new UserDto(
                user.getUserId(),
                user.getFirst(),
                user.getLast(),
                user.getEmail(),
                user.getApiKey(),
                yardIds
        );
    }
}











