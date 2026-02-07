package com.project.library.controller;

import com.project.library.model.Author;
import com.project.library.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = "/author/{id}", produces = APPLICATION_JSON_VALUE)
    public Author getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }


}
