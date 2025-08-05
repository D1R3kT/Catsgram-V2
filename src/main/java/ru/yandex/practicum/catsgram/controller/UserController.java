package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exceptions.DublicatedDataException;
import ru.yandex.practicum.catsgram.exceptions.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isBlank()) {
            throw new ConditionsNotMetException("email должен быть указан");
        }
        for (User user : users.values()) {
            if (newUser.getEmail().equals(user.getEmail())) {
                throw new DublicatedDataException("Данный email уже используется");
            }
        }

        newUser.setId(createId());
        newUser.setRegistrationDate(Instant.now());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("id пользователя должен быть указан");
        }

        if (newUser.getEmail() != null || !newUser.getEmail().isBlank()) {
            for (User user : users.values()) {
                if (newUser.getEmail().equals(user.getEmail())) {
                    throw new DublicatedDataException("email = " + newUser.getEmail() + " уже занят");
                }
            }
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (newUser.getEmail() != null) {
                oldUser.setEmail(newUser.getEmail());
            }
            oldUser.setUsername(newUser.getUsername());
            users.put(newUser.getId(), oldUser);
            return oldUser;

        } else {
            throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден!");
        }

    }


    private int createId() {
        return id++;
    }
}
