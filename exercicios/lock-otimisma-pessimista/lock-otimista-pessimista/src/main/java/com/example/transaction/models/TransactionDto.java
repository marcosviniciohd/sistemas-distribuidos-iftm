package com.example.transaction.models;

public record TransactionDto<T extends Number>(Long id, T value) {
}
