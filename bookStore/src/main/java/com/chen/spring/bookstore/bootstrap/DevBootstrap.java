package com.chen.spring.bookstore.bootstrap;

import com.chen.spring.bookstore.model.Author;
import com.chen.spring.bookstore.model.Book;
import com.chen.spring.bookstore.model.Publisher;
import com.chen.spring.bookstore.repositories.AuthorRepository;
import com.chen.spring.bookstore.repositories.BookRepository;
import com.chen.spring.bookstore.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 5/16/17.
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){

        Publisher penguin = new Publisher("Penguin Inc", "123, Penguin Drive, North Pole");
        Publisher wrox = new Publisher("Wrox", "123, Wrox Drive, Washington");
        Publisher harperCollions = new Publisher("Harper Collins", "123, HC Drive, South Carolina");

        publisherRepository.save(penguin);
        publisherRepository.save(wrox);
        publisherRepository.save(harperCollions);

        //Eric
        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "1234", wrox);
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);


        //Rod
        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "23444", harperCollions);
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
    }
}
