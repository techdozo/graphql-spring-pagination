package dev.techdozo.graphql.controller;

import dev.techdozo.graphql.domain.BookConnection;
import dev.techdozo.graphql.domain.model.*;
import dev.techdozo.graphql.domain.service.BookCatalogService;
import lombok.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BooksCatalogController {

  private final BookCatalogService bookCatalogService;

  @QueryMapping()
  public BookConnection books(
      @Argument("first") Integer first,
      @Argument("after") String after,
      @Argument("last") Integer last,
      @Argument("before") String before) {
    return bookCatalogService.getBookConnection(new CursorInfo(first, after, last, before));
  }
}
