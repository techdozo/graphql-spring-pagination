package dev.techdozo.graphql.domain.service;

import dev.techdozo.graphql.domain.*;

import java.util.*;

import dev.techdozo.graphql.domain.model.*;
import dev.techdozo.graphql.domain.repository.*;
import lombok.*;

import lombok.extern.slf4j.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookCatalogService {

  private final BookRepository bookRepository;

  public BookConnection getBookConnection(CursorInfo cursorInfo) {

    int pageSize = cursorInfo.pageSize();

    // Limit is pageSize + 1
    var limit = pageSize + 1;
    List<BookResult> bookResults;
    boolean hasNextPage;
    boolean hasPreviousPage;
    String startCursor = "";
    String endCursor = "";

    if (cursorInfo.hasCursors() && cursorInfo.hasNextPageCursor()) {
      bookResults =
          bookRepository.booksWithNextPageCursor(CursorInfo.decode(cursorInfo.getCursor()), limit);
      int resultSize = bookResults.size();
      var firstResult = bookResults.get(0);
      hasPreviousPage = firstResult.hasPage();
      startCursor = CursorInfo.encode(firstResult.id());
      int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
      endCursor = CursorInfo.encode(bookResults.get(endCursorIndex).id());
      hasNextPage = resultSize > pageSize;

    } else if (cursorInfo.hasCursors() && cursorInfo.hasPrevPageCursor()) {
      bookResults =
          bookRepository.booksWithPreviousPageCursor(
              CursorInfo.decode(cursorInfo.getCursor()), limit);
      int resultSize = bookResults.size();
      var firstResult = bookResults.get(0);
      hasNextPage = firstResult.hasPage();
      startCursor = CursorInfo.encode(firstResult.id());
      int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
      endCursor = CursorInfo.encode(bookResults.get(endCursorIndex).id());
      hasPreviousPage = resultSize > pageSize;
    } else {
      bookResults = bookRepository.booksWithoutCursor(limit);
      int resultSize = bookResults.size();
      hasPreviousPage = false;
      var firstResult = bookResults.get(0);
      startCursor = CursorInfo.encode(firstResult.id());
      int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
      endCursor = CursorInfo.encode(bookResults.get(endCursorIndex).id());
      hasNextPage = resultSize > pageSize;
    }

    // TODO check what results are returned when requested out of range
    if (bookResults.size() == 0) {
      return new BookConnection(null, new BookInfo(null, null, false, false));
    }

    var bookEdges =
        bookResults.stream()
            .limit(cursorInfo.pageSize())
            .map(
                bookResult ->
                    new BookEdge(
                        CursorInfo.encode(bookResult.id()),
                        new Book(
                            bookResult.id(),
                            bookResult.name(),
                            bookResult.author(),
                            bookResult.price())))
            .toList();

    var bookConnection =
        new BookConnection(
            bookEdges, new BookInfo(startCursor, endCursor, hasNextPage, hasPreviousPage));
    log.info("Pages, {}", bookConnection);
    return bookConnection;
  }
}
