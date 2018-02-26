package com.oyster.graphqldemo.repository;

import com.oyster.graphqldemo.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
