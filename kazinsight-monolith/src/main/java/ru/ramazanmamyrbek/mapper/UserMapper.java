package ru.ramazanmamyrbek.mapper;

import lombok.experimental.UtilityClass;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;

@UtilityClass
public class UserMapper {
    public User newUserPayloadToUser(NewUserPayload newUserPayload, User user) {
        user.setEmail(newUserPayload.getEmail());
        user.setPassword(newUserPayload.getPassword());
        user.setFirstName(newUserPayload.getFirstName());
        user.setLastName(newUserPayload.getLastName());
        return user;
    }
}
