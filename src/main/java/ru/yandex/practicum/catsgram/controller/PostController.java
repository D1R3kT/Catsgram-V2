package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exceptions.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final Map<Integer, Post> posts = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<Post> findAll() {
        return posts.values();
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        if(post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }
        post.setId(createId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        if(newPost.getId() == null) {
            throw new ConditionsNotMetException("id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return posts.put(oldPost.getId(), oldPost);
        } else {
            throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
        }
    }

    private Integer createId() {
        return id++;   }
}
