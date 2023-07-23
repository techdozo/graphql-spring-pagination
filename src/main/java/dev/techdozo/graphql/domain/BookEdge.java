package dev.techdozo.graphql.domain;

import dev.techdozo.graphql.domain.model.Book;

public record BookEdge(String cursor, Book node) {}
