package com.oyster.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.oyster.graphqldemo.model.Author;
import com.oyster.graphqldemo.model.Book;
import com.oyster.graphqldemo.repository.AuthorRepository;
import com.oyster.graphqldemo.repository.BookRepository;

public class Query implements GraphQLQueryResolver {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Iterable<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors(){
        return authorRepository.findAll();
    }

    public long countBooks(){
        return bookRepository.count();
    }

    public long countAuthors(){
        return authorRepository.count();
    }

}
