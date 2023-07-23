package dev.techdozo.graphql.domain;


public record BookInfo(String startCursor, String endCursor, Boolean hasNextPage, Boolean hasPreviousPage) {}
