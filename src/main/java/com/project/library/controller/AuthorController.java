package com.project.library.controller;

import com.project.library.model.Author;
import com.project.library.model.Book;
import com.project.library.service.AuthorService;
import com.project.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = "/author/{id}", produces = APPLICATION_JSON_VALUE)
    public Author getAuthor(@PathVariable Long id){
        return authorService.getAuthor(id);
    }

    /*@PostMapping(path = "/book", consumes = APPLICATION_JSON_VALUE)
    public void upsertAuthor(Book book){
        authorService.createUpdateAuthor(author);
    }*/
}
