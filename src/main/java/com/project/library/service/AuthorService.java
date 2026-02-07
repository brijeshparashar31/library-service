package com.project.library.service;

import com.project.library.entity.AuthorEntity;
import com.project.library.exception.ObjectNotFoundException;
import com.project.library.mapper.LibraryRequestResponseMapper;
import com.project.library.model.Author;
import com.project.library.model.Book;
import com.project.library.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    private LibraryRequestResponseMapper mapper;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Long id) {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findById(id);
        if (authorEntityOptional.isEmpty()) {
            throw new ObjectNotFoundException("Requested Resource not found");
        }
        return mapper.mapAuthorEntityToAuthorWithBook(authorEntityOptional.orElse(null));
    }

    public void addBookToAuthor(Long authorId, Book book) {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findById(authorId);
        if (authorEntityOptional.isEmpty()) {
            throw new ObjectNotFoundException("Author not found with requested Id");
        }
        var authorEntity = authorEntityOptional.get();
        var bookEntity = mapper.mapBookToBookEntity(book);
        bookEntity.setAuthor(authorEntity);
        authorEntity.getBooks().add(bookEntity);
        var savedauthorEntity = authorRepository.save(authorEntity);
        log.debug("savedAuthorEntity {}", savedauthorEntity);
    }
}
