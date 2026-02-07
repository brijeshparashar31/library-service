package com.project.library.service;

import com.project.library.entity.BookEntity;
import com.project.library.exception.ObjectNotFoundException;
import com.project.library.mapper.LibraryRequestResponseMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LibraryRequestResponseMapper mapper;

    public BookService(BookRepository bookRepository, LibraryRequestResponseMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public Book getBook(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if (bookEntity.isEmpty()) {
            throw new ObjectNotFoundException("Requested Resource not found");
        }
        return mapper.mapBookEntityToBookWithAuthor(bookEntity.orElse(null));
    }
}
