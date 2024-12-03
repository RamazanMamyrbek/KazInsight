package ru.ramazanmamyrbek.kazinsightmonolith.controller.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserPayload {
    @Email(message = "users.errors.email_is_not_valid")
    private String email;
    @Size(min = 5, max = 20, message = "users.errors.password_size_is_invalid")
    private String password;
    @Size(min = 1, max = 100, message = "users.errors.firstName_size_is_invalid")
    private String firstName;
    @Size(min = 1, max = 100, message = "users.errors.lastName_size_is_invalid")
    private String lastName;
}
