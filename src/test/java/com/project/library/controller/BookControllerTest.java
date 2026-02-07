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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetBookRequested_thenReturnBookDetails() {
        HttpEntity request = new HttpEntity(getHeaders());
        ResponseEntity<Book> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book/10000001",
                HttpMethod.GET,
                request,
                Book.class
        );
        assertEquals("A Study in Scarlet", response.getBody().getTitle());
    }

    @Test
    public void whenInvalidBookIdRequested_thenReturnErrorMessage(){
        HttpEntity request = new HttpEntity(getHeaders());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book/999",
                HttpMethod.GET,
                request,
                ErrorResponse.class
        );
        assertEquals(HttpStatusCode.valueOf(404),response.getStatusCode());
        assertEquals("ER001", response.getBody().getErrorCode());
        assertEquals("Requested Resource not found", response.getBody().getErrorMessage());
    }

    @Test
    public void whenBookSavedWithExistingAuthor_thenBookSavedSuccessfully(){
        HttpEntity request = new HttpEntity(getBookPayload(), getHeaders());
        ResponseEntity<Book> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book",
                HttpMethod.POST,
                request,
                Book.class
        );

        HttpEntity requestGetAuthor = new HttpEntity(getHeaders());
        ResponseEntity<Author> responseGetAuthor = restTemplate.exchange(
                "http://localhost:" + port + "/library/author/1",
                HttpMethod.GET,
                requestGetAuthor,
                Author.class
        );
        var bookListFetchedFromDB = responseGetAuthor.getBody().getBooks().stream().map(Book::getTitle).toList();
        assertTrue(bookListFetchedFromDB.contains("Ready or not"));
    }

    @Test
    public void whenCreateBookRequestedWithInvalidAuthor_thenReturnErrorMessage(){
        var book = getBookPayload();
        book.getAuthor().setId(0L);
        HttpEntity request = new HttpEntity(book, getHeaders());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book",
                HttpMethod.POST,
                request,
                ErrorResponse.class
        );
        assertEquals(HttpStatusCode.valueOf(404),response.getStatusCode());
        assertEquals("ER001", response.getBody().getErrorCode());
        assertEquals("Author not found with requested Id", response.getBody().getErrorMessage());
    }

    @Test
    public void whenCreateBookRequestedWithTitleAsNull_thenReturnBadRequest(){
        var book = getBookPayload();
        book.setTitle(null);
        book.getAuthor().setId(0L);
        HttpEntity request = new HttpEntity(book, getHeaders());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book",
                HttpMethod.POST,
                request,
                ErrorResponse.class
        );
        assertEquals(HttpStatusCode.valueOf(400),response.getStatusCode());
        assertEquals("ER002", response.getBody().getErrorCode());
    }

    @Test
    public void whenCreateBookRequestedWithIdProvided_thenReturnInternalServerError(){
        var book = getBookPayload();
        book.setId(3L);
        HttpEntity request = new HttpEntity(book, getHeaders());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/library/book",
                HttpMethod.POST,
                request,
                ErrorResponse.class
        );
        assertEquals(HttpStatusCode.valueOf(500),response.getStatusCode());
        assertEquals("ER004", response.getBody().getErrorCode());
    }


    private Book getBookPayload() {
        return Book.builder()
                .title("Ready or not")
                .author(Author.builder()
                        .id(1L)
                        .build())
                .build();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }
}