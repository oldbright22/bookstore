package com.service.bookstore.contract;

import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import com.service.bookstore.repository.BookDAOImpl;
import com.service.bookstore.service.BookService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
public class BookstoreControllerContractTests2 {

    @Spy
    private BookDAOImpl daoSpy;

    @InjectMocks
    private BookService service;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void test() {

        Book book = new Book(UUID.randomUUID(), "3The Hunger Games", "Suzanne Collins", 39.99, 5.00, LocalDate.now().minusYears(5), BookStatus.AVAILABLE, BookRatings.GREEN);

        assertThat(service.addBook(book), is(false));

        verify(daoSpy).saveBook(any(Book.class));

        verify(daoSpy, times(1)).getBook(book.getId());

        verify(daoSpy, never()).deleteBook(any(Book.class));
    }
}
