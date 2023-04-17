package com.service.bookstore.controller;

import com.service.bookstore.exceptions.BookNotFoundException;
import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import com.service.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookstore")
@Slf4j
public class BookController {

    // dependency injection- spring servlet container
    @Autowired
    BookService bookService;

    @Autowired
    private HttpServletRequest request;
   /* public BookController(BookService bookService){
        this.bookService=bookService;
    }*/


   /* @Bean
    public BookService getBookRepoObject(){
        return new BookService();
    }*/



    @GetMapping(path = "/Books")
    public List<Book> getAllBooks() {
        return bookService.retrieveAllBooks();
    }

    @GetMapping(path = "/Books/{BookName}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksByName(@PathVariable String BookName) {
        return bookService.getBooksByName(BookName);
    }

    @GetMapping(path = "/Books/{BookAuthor}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksByAuthor(@PathVariable String BookAuthor) {
        return bookService.getBooksByAuthor(BookAuthor);
    }

    @GetMapping(path = "/Books/{BookStatus}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksByName(@PathVariable BookStatus BookStatus) {
        return bookService.getBooksByStatus(BookStatus);
    }

    @GetMapping(path = "/Books/{BookRating}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksByAuthor(@PathVariable BookRatings BookRating) {
        return bookService.getBooksByRating(BookRating);
    }

    @SneakyThrows
    @GetMapping(path = "/Book/{BookName}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book>  getBookByName(@PathVariable String BookName) {
        Book book= bookService.getBookByName(BookName);
        if(book==null) throw new BookNotFoundException("Book with name"+ BookName +" not found");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(path = "/Book/{BookAuthor}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book>  getBookByAuthor(@PathVariable String BookAuthor) {
        Book book= bookService.getBookByAuthor(BookAuthor);
        if(book==null) throw new BookNotFoundException("Book with id"+ BookAuthor +" not found");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(value = "/Book/{BookId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getSpecificBook(@PathVariable UUID BookId){
        Book Book = bookService.getBook(BookId);
        if(Book==null) throw new BookNotFoundException("Book with id"+ BookId.toString() +" not found");
        return new ResponseEntity<>(Book, HttpStatus.OK);
    }


    // create a new Book
    // request body, context url, header
    @PostMapping(path = "/Book/create")
    public ResponseEntity<Book> createBook( @Valid @RequestBody Book book){
        if(book==null) return  ResponseEntity.internalServerError().body(book);
        Book newBook = bookService.addBook(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBook.getId())
                .toUri();
        // get a specific Book
        return ResponseEntity.created(uri).body(newBook);
    }

    // delete a Book
    @DeleteMapping("/Book/delete/{BookId}")
    public ResponseEntity deleteBook(@PathVariable UUID BookId){
        bookService.deleteBook(BookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/Book/delete/{BookName}")
    public ResponseEntity deleteBookByName(@PathVariable String BookName){
        bookService.deleteBookByName(BookName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/Book/delete/{BookAuthor}")
    public ResponseEntity deleteBookByAuthor(@PathVariable String BookAuthor){
        bookService.deleteBookByAuthor(BookAuthor);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/Book/{BookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable UUID BookId){
        if (book.getBookName()==null && book.getBookAuthor()==null && book.getPublishedDate()==null && book.getBookPrice()==0.00){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity(bookService.updateBook(BookId, book), HttpStatus.OK);
    }


    @PutMapping("/Book/upsert")
    public ResponseEntity<Book> updateUpsert(@RequestBody Book book){

        // find Book
        Book returnedBook = bookService.getBook(book.getId());
        // if  not exist then create and return
        if(returnedBook==null) {
            ResponseEntity<Book> book1 = createBook(book);
            return new ResponseEntity(book1.getBody(), HttpStatus.CREATED);
        }
        // if yes return the updated Book
        BookService.updateBook(returnedBook,book);
        return new ResponseEntity<>(returnedBook,HttpStatus.OK);
    }

}