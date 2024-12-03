package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Role;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.RoleName;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.UserRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;
import ru.ramazanmamyrbek.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("users.errors.not_found"));
    }

    @Override
    @Transactional
    public User saveUser(NewUserPayload newUserPayload) {
        User user = new User();
        newUserPayload.setPassword(passwordEncoder.encode(newUserPayload.getPassword()));
        user = UserMapper.newUserPayloadToUser(newUserPayload, user);
        user.setRoles(List.of(Role.builder().name(RoleName.ROLE_USER).build()));
        return userRepository.save(user);
    }
}
