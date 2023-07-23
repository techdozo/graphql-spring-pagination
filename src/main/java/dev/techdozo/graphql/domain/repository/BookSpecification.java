package dev.techdozo.graphql.domain.repository;

import dev.techdozo.graphql.domain.model.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.*;



@RequiredArgsConstructor
public class BookSpecification<T> implements Specification<T> {

  private final String sortField;
  private final CursorInfo cursorInfo;

  @Override
  public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
//    cursorInfo.hasNextPageCursor()?
//    query.orderBy(criteriaBuilder.asc(root.get(sortField)));
    return null;
  }
}
