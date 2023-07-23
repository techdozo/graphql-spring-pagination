package dev.techdozo.graphql.domain;

import java.util.List;

public record BookConnection(List<BookEdge> edges, BookInfo pageInfo) {}
