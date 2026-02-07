package com.project.library.controller;

import com.project.library.LibraryApplication;
import com.project.library.model.Author;
import com.project.library.model.Book;
import com.project.library.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LibraryApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenAuthorRequested_thenReturnAuthorWithAllListOfBooks() throws Exception {
        HttpEntity request = new HttpEntity(getHeaders());
        ResponseEntity<Author> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/author/2",
                HttpMethod.GET,
                request,
                Author.class
        );
        List<String> bookList = List.of("A Study in Scarlet", "The Valley of Fear");
        assertEquals(2, response.getBody().getBooks().size());
        var bookListFetchedFromDB = response.getBody().getBooks().stream().map(Book::getTitle).toList();
        assertTrue(bookListFetchedFromDB.containsAll(bookList));
    }

    @Test
    public void whenInvalidAuthorRequested_thenReturnErrorMessage() throws Exception {
        HttpEntity request = new HttpEntity(getHeaders());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/author/0",
                HttpMethod.GET,
                request,
                ErrorResponse.class
        );
        assertEquals(HttpStatusCode.valueOf(404),response.getStatusCode());
        assertEquals("ER001", response.getBody().getErrorCode());
        assertEquals("Requested Resource not found", response.getBody().getErrorMessage());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }
}