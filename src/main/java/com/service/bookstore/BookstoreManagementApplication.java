package com.service.bookstore;

import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import com.service.bookstore.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
//@EnableEurekaClient
public class BookstoreManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreManagementApplication.class, args);
	}


	@Bean
	BookService repository() {
		BookService repo = new BookService();
		repo.addBook(new Book(UUID.randomUUID(), "2The Hunger Games", "Suzanne Collins", 39.99, 5.00, LocalDate.now().minusYears(5), BookStatus.AVAILABLE, BookRatings.GREEN  ));
		repo.addBook(new Book(UUID.randomUUID(), "2Catching Fire", "Suzanne Collins", 69.99, 10.00, LocalDate.now().minusYears(4) ,BookStatus.SOLDOUT, BookRatings.YELLOW ));
		repo.addBook(new Book(UUID.randomUUID(), "2Gone With the Wind", "Margaret Mitchell", 899.99, 150.00, LocalDate.now().minusYears(10), BookStatus.AVAILABLE, BookRatings.GREEN ));
		return repo;
	}
}
