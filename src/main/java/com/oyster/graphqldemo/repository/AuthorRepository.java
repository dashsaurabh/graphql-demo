package com.oyster.graphqldemo.repository;

import com.oyster.graphqldemo.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
