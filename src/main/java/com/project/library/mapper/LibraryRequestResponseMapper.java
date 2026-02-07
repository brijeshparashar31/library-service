package com.project.library.mapper;

import com.project.library.entity.AuthorEntity;
import com.project.library.entity.BookEntity;
import com.project.library.model.Author;
import com.project.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper class
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LibraryRequestResponseMapper {


    BookEntity mapBookToBookEntity(Book book);

    @Mapping(target = "author", expression = "java(mapAuthorEntityToAuthor(bookEntity.getAuthor()))")
    Book mapBookEntityToBookWithAuthor(BookEntity bookEntity);

    @Mapping(target = "author", ignore = true)
    Book mapBookEntityToBook(BookEntity bookEntity);

    @Mapping(target = "books", ignore = true)
    Author mapAuthorEntityToAuthor(AuthorEntity authorEntity);

    @Mapping(target = "books", expression = "java(mapBookEntityListToBookList(authorEntity.getBooks()))")
    Author mapAuthorEntityToAuthorWithBook(AuthorEntity authorEntity);


    default List<Book> mapBookEntityListToBookList(List<BookEntity> books) {
        return books.stream().map(bookEntity -> mapBookEntityToBook(bookEntity)).collect(Collectors.toUnmodifiableList());
    }

}
