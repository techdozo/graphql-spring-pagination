package dev.techdozo.graphql.domain.repository;

import dev.techdozo.graphql.domain.repository.model.BookEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

  @Query(nativeQuery = true)
  List<BookResult> booksWithNextPageCursor(Integer id, int limit);

  @Query(nativeQuery = true)
  List<BookResult> booksWithPreviousPageCursor(Integer id, int limit);

  @Query(nativeQuery = true)
  List<BookResult> booksWithoutCursor(int limit);
}
