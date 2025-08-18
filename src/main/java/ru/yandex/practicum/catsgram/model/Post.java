package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"id"})
public class Post {
    Integer id;
    Integer authorId;
    String description;
    Instant postDate;
}
