package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"id"})
public class Post {
    UUID id;
    UUID authorId;
    String description;
    Instant postDate;
}
