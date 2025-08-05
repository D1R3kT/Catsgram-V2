package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class Post {
    Long id;
    Long authorId;
    String description;
    Instant postDate;
}
