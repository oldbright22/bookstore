package com.service.bookstore.repository;


import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> booksByName(String bookName);
    List<Book> booksByAuthor(String bookAuthor);

    List<Book> booksByStatus(BookStatus bookStatus);
    List<Book> booksByRating(BookRatings bookRating);

    Book findBookByName(String bookName);
    Book findBookByAuthor(String bookAuthor);
    void  deleteBookByName(String bookName);
    void  deleteBookByAuthor(String bookAuthor);

    Book updateBook(UUID BookId, Book book);

}
