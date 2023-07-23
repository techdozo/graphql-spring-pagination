package dev.techdozo.graphql.domain.repository.model;

import dev.techdozo.graphql.domain.repository.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NamedNativeQuery(
    name = "BookEntity.booksWithNextPageCursor",
    query =
        """
            SELECT id,name, author, price,
            EXISTS (SELECT 1 FROM book WHERE id < ?1) AS has_page
            FROM book
            WHERE id > ?1
            ORDER BY id
            LIMIT ?2
            """,
    resultSetMapping = "Mapping.BookResult")
@NamedNativeQuery(
    name = "BookEntity.booksWithPreviousPageCursor",
    query =
        """
            SELECT id,name, author, price,
            EXISTS (SELECT 1 FROM book WHERE id > ?1) AS has_page
            FROM book
            WHERE id < ?1
            ORDER BY id
            LIMIT ?2
        """,
    resultSetMapping = "Mapping.BookResult")
@NamedNativeQuery(
    name = "BookEntity.booksWithoutCursor",
    query =
        """
            SELECT id,name, author, price,
            false AS has_page
            FROM book
            ORDER BY ID LIMIT ?1
         """,
    resultSetMapping = "Mapping.BookResult")
@SqlResultSetMapping(
    name = "Mapping.BookResult",
    classes =
        @ConstructorResult(
            targetClass = BookResult.class,
            columns = {
              @ColumnResult(name = "id"),
              @ColumnResult(name = "name"),
              @ColumnResult(name = "author"),
              @ColumnResult(name = "price"),
              @ColumnResult(name = "has_page")
            }))
@Entity
@Table(name = "book")
public class BookEntity {
  @Id private Integer id;
  private String name;
  private String author;
  private Double price;
}
