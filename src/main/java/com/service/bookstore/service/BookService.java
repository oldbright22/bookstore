package com.service.bookstore.service;


import com.service.bookstore.exceptions.BookNotFoundException;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import com.service.bookstore.repository.BookRepository;
import com.service.bookstore.model.Book;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class BookService {


    @Autowired
    BookRepository BookRepository;

    public static ArrayList<Book> Books = new ArrayList<>();
    public static UUID id;

    static {
        Books.add(new Book(UUID.randomUUID(), "The Hunger Games", "Suzanne Collins", 39.99, 5.00, LocalDate.now().minusYears(5), BookStatus.AVAILABLE, BookRatings.GREEN  ));
        Books.add(new Book(UUID.randomUUID(), "Catching Fire", "Suzanne Collins", 69.99, 10.00, LocalDate.now().minusYears(4) ,BookStatus.SOLDOUT, BookRatings.YELLOW ));
        Books.add(new Book(UUID.randomUUID(), "Gone With the Wind", "Margaret Mitchell", 899.99, 150.00, LocalDate.now().minusYears(10), BookStatus.AVAILABLE, BookRatings.GREEN ));
    }

    public List<Book> retrieveAllBooks() {
        return BookRepository.findAll();
        //  return Books;
    }


    public  Book getBook(UUID BookId) {
        Book Book = BookRepository.findById(BookId).get();
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
        return Book;
    }


    public  Book getBookByName(String BookName) {
        return BookRepository.findBookByName(BookName);
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }

    public  Book getBookByAuthor(String BookAuthor) {
        return BookRepository.findBookByAuthor(BookAuthor);
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }

    public  List<Book> getBooksByName(String BookName) {
        return BookRepository.booksByName(BookName).stream().distinct().toList();
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }

    public  List<Book> getBooksByAuthor(String BookAuthor) {
        return BookRepository.booksByAuthor(BookAuthor).stream().distinct().toList();
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }

    public  List<Book> getBooksByStatus(BookStatus BookStatus) {
        return BookRepository.booksByStatus(BookStatus).stream().distinct().toList();
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }

    public  List<Book> getBooksByRating(BookRatings BookRating) {
        return BookRepository.booksByRating(BookRating).stream().distinct().toList();
        //Optional<Book> byId = BookRepository.findById(BookId);
        //if (byId.isEmpty()) return null;
    }
    public Book addBook(Book Book) {
        Book save = BookRepository.save(Book);
        return save;
    }

    public  void deleteBook(UUID BookId) {
        // Book is present or not then delete
        BookRepository.deleteById(BookId);
        //  Books.removeIf(x -> x.getId() == BookId);
    }

    public  void deleteBookByName(String BookName) {
        // Book is present or not then delete
        Book delBook = getBookByName(BookName);
        if (delBook != null)
                deleteBook(delBook.getId());
    }

    public  void deleteBookByAuthor(String BookAuthor) {
        // Book is present or not then delete
        Book delBook = getBookByAuthor(BookAuthor);
        if (delBook != null)
            deleteBook(delBook.getId());
    }

    @SneakyThrows
    public Book updateBook(UUID BookId, Book book) {
        Book newBook = BookRepository.findById(BookId).get();
        //  Book newBook = Books.stream().filter(x -> x.getId() == BookId).findFirst().orElse(null);
        if (newBook == null) throw new BookNotFoundException("Book with " + BookId.toString() + " not found");
        newBook.setBookName(book.getBookName());
        newBook.setBookAuthor(book.getBookAuthor());
        newBook.setBookPrice(book.getBookPrice());
        newBook.setDiscountedPrice(book.getDiscountedPrice());
        newBook.setPublishedDate(book.getPublishedDate());
        BookRepository.save(newBook);
        return newBook;
    }

    public static Book updateBook(Book returnedBook, Book book) {
        returnedBook.setBookName(book.getBookName());
        returnedBook.setBookAuthor(book.getBookAuthor());
        returnedBook.setBookPrice(book.getBookPrice());
        returnedBook.setDiscountedPrice(book.getDiscountedPrice());
        returnedBook.setPublishedDate(book.getPublishedDate());
        return returnedBook;
    }



}