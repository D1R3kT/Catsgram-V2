package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"email"})
public class User {
    UUID id;
    String username;
    String email;
    String password;
    Instant registrationDate;
}
