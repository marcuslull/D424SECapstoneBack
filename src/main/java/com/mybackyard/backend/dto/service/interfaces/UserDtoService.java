package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.UserDto;
import com.mybackyard.backend.model.User;

import java.util.List;

public interface UserDtoService {
    List<UserDto> processOutgoingUserList(List<User> userList);

    UserDto processOutgoingUser(User user);

    User processIncomingUserDto(UserDto userDto, boolean isFromPatch);

    UserDto addId(UserDto userDto, long userId);
}
