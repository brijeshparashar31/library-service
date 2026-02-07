package com.project.library.controller;

import com.project.library.model.Book;
import com.project.library.service.AuthorService;
import com.project.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @GetMapping(path = "/book/{id}", produces = APPLICATION_JSON_VALUE)
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id.longValue());
    }

    @PostMapping(path = "/book", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody @Valid Book book) {
        authorService.addBookToAuthor(book.getAuthor().getId(), book);
    }

}
