package ru.ramazanmamyrbek.kazinsightmonolith.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramazanmamyrbek.kazinsightmonolith.controller.payload.NewUserPayload;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Place;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.Role;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.User;
import ru.ramazanmamyrbek.kazinsightmonolith.entity.enums.RoleName;
import ru.ramazanmamyrbek.kazinsightmonolith.exception.UserNotFoundException;
import ru.ramazanmamyrbek.kazinsightmonolith.repository.UserRepository;
import ru.ramazanmamyrbek.kazinsightmonolith.service.PlaceService;
import ru.ramazanmamyrbek.kazinsightmonolith.service.UserService;
import ru.ramazanmamyrbek.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceService placeService;
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


    @Override
    @Transactional
    public void replenishBalance(Long userId, Double value) {
        User user = getUserById(userId);
        user.setBalance(user.getBalance()+value);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("users.errors.not_found_by_id"));
    }

    @Override
    @Transactional
    public void resetBalance(Long userId) {
        User user = getUserById(userId);
        user.setBalance((double)0);
        userRepository.save(user);
    }

    @Override
    public List<Place> getFavorites(Long userId) {
        User user = getUserById(userId);
        return user.getFavoritePlaces();
    }

    @Override
    @Transactional
    public void addPlaceToFavorites(String email, Long placeId) {
        User user = getUserByEmail(email);
        Place place = placeService.findPlace(placeId);
        user.getFavoritePlaces().add(place);
        place.getFavoriteUsers().add(user);
        userRepository.save(user);
        placeService.savePlace(place);
    }

    @Override
    @Transactional
    public void removePlaceFromFavorites(String email, Long placeId) {
        User user = getUserByEmail(email);
        Place place = placeService.findPlace(placeId);
        user.getFavoritePlaces().remove(place);
        place.getFavoriteUsers().remove(user);
        userRepository.save(user);
        placeService.savePlace(place);
    }
}
