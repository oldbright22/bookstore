package com.service.bookstore.repository;

import com.service.bookstore.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookDAO {

    public List<Book> getBooks();

    public void saveBook(Book theBook);

    public Book getBook(UUID theId);

    public void deleteBook(Book theBook);
}
