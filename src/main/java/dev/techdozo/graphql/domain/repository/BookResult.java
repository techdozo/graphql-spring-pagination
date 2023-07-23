package dev.techdozo.graphql.domain.repository;

public record BookResult(
    Integer id, String name, String author, Double price, Boolean hasPage) {}
