package com.project.library.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOK")
@Getter
@Setter
@EqualsAndHashCode
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKID")
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "AUTHORID")
    private AuthorEntity author;

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
