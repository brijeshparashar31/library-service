package com.project.library.repository;

import com.project.library.entity.AuthorEntity;
import com.project.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
