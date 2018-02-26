package com.oyster.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.oyster.graphqldemo.model.Author;
import com.oyster.graphqldemo.model.Book;
import com.oyster.graphqldemo.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findOne(book.getAuthor().getId());
    }
}

