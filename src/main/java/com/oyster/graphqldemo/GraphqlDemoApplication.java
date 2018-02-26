package com.oyster.graphqldemo;

import com.oyster.graphqldemo.errors.GraphQLErrorAdapter;
import com.oyster.graphqldemo.model.Author;
import com.oyster.graphqldemo.model.Book;
import com.oyster.graphqldemo.repository.AuthorRepository;
import com.oyster.graphqldemo.repository.BookRepository;
import com.oyster.graphqldemo.resolver.BookResolver;
import com.oyster.graphqldemo.resolver.Mutation;
import com.oyster.graphqldemo.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class GraphqlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlDemoApplication.class, args);
	}

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository){
		return new BookResolver(authorRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository){
		return new Query(bookRepository,authorRepository);
	}

	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository){
		return new Mutation(bookRepository,authorRepository);
	}

	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository){
		return (args) -> {
			Author author = new Author("Herbert","Schildt");
			authorRepository.save(author);

			bookRepository.save(new Book("Java: A Beginner's Guide","0078999",728, author));
		};
	}

	@Bean
	public GraphQLErrorHandler errorHandler(){
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			private boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}
}
