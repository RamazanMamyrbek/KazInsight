package ru.ramazanmamyrbek.kazinsightmonolith.service;

import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;

public interface UserService {
    User getUserByEmail(String email);

    User saveUser(NewUserPayload newUserPayload);
}
