package com.oyster.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.oyster.graphqldemo.errors.BookNotFoundException;
import com.oyster.graphqldemo.model.Author;
import com.oyster.graphqldemo.model.Book;
import com.oyster.graphqldemo.repository.AuthorRepository;
import com.oyster.graphqldemo.repository.BookRepository;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Author newAuthor(String firstName, String lastName){
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);
        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId){
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);
        return book;
    }

    public boolean deleteBook(Long id){
        bookRepository.delete(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id){
        Book book = bookRepository.findOne(id);
        if(book == null){
            throw new BookNotFoundException("The book to be updated not found",id);
        }

        book.setPageCount(pageCount);

        bookRepository.save(book);
        return book;
    }

}
