package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exceptions.DublicatedDataException;
import ru.yandex.practicum.catsgram.exceptions.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getAll() {
        return users.values();
    }

    public User create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("email должен быть указан");
        }
        for (User u : users.values()) {
            if (user.getEmail().equals(u.getEmail())) {
                throw new DublicatedDataException("Данный email уже используется");
            }
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
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

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
