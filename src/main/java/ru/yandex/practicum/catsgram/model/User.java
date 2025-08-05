package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class User {
    Long id;
    String username;
    String email;
    String password;
    Instant registrationDate;
}
