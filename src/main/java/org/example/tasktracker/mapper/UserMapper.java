package org.example.tasktracker.mapper;

import org.example.tasktracker.dto.UserRegistrationRequestDto;
import org.example.tasktracker.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserRegistrationRequestDto userRegistrationRequestDto);
}
