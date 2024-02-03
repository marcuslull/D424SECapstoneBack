package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.UserDtoMapper;
import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.dto.service.interfaces.UserDtoService;
import com.mybackyard.backend.model.User;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoMapper userDtoMapper;
    private final DtoValidator dtoValidator;

    public UserDtoServiceImpl(UserDtoMapper userDtoMapper, DtoValidator dtoValidator) {
        this.userDtoMapper = userDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<UserDto> processOutgoingUserList(List<User> userList) {
        return userDtoMapper.usersToDtos(userList);
    }

    @Override
    public UserDto processOutgoingUser(User user) {
        return userDtoMapper.userToDto(user);
    }

    @Override
    public User processIncomingUserDto(UserDto userDto, boolean isFromPatch) {
        if (!dtoValidator.isWellFormed(userDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return userDtoMapper.dtoToUser(userDto, isFromPatch);
    }

    @Override
    public UserDto addId(UserDto userDto, long userId) {
        return new UserDto(userId, userDto.first(), userDto.last(), userDto.email(), userDto.apiKey(),
                userDto.yardIds());
    }
}
