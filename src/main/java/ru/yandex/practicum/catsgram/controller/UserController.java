package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getAll();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userService.create(newUser);
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        return userService.update(newUser);
    }
}
