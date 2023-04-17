package com.service.bookstore.repository;


import com.service.bookstore.model.Book;
import com.service.bookstore.model.BookRatings;
import com.service.bookstore.model.BookStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;



// https://github.com/chaudharyjr/spring-mvc-crud/blob/master/src/com/chaudharyjr/springmvc/DAO/CustomerDAOImpl.java
//https://stackoverflow.com/questions/53739152/error-creating-bean-with-name-customercontroller-unsatisfied-dependency-expre
//https://github.com/querydsl/querydsl/blob/master/querydsl-examples/querydsl-example-sql-spring/src/main/java/com/querydsl/example/dao/CustomerDaoImpl.java
@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Book> getBooks() {

        Session session = sessionFactory.getCurrentSession();
        Query<Book> theQuery = session.createQuery("from Book", Book.class);

        // Execute and get the results
        List<Book> bookList = theQuery.getResultList();

        return bookList;
    }


    @Override
    public void saveBook(Book theBook) {
        Session session=sessionFactory.getCurrentSession();
        session.saveOrUpdate(theBook);
    }

    @Override
    public Book getBook(UUID theId) {
        Session session=sessionFactory.getCurrentSession();
        Book book=session.get(Book.class, theId);
        return book;
    }

    @Override
    public void deleteBook(Book theBook) {
        Session session=sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Book where id =:value");
        query.setParameter("value", theBook.getId());
        query.executeUpdate();
    }


}

/*

    // Inject the SessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<Book> booksByName(String bookName) {
        return null;
    }

    @Override
    public List<Book> booksByAuthor(String bookAuthor) {
        return null;
    }

    @Override
    public List<Book> booksByStatus(BookStatus bookStatus) {
        return null;
    }

    @Override
    public List<Book> booksByRating(BookRatings bookRating) {
        return null;
    }

    @Override
    public Book findBookByName(String bookName) {
        return null;
    }

    @Override
    public Book findBookByAuthor(String bookAuthor) {
        return null;
    }

    @Override
    public void deleteBookByName(String bookName) {

    }

    @Override
    public void deleteBookByAuthor(String bookAuthor) {

    }

    @Override
    public Book updateBook(UUID BookId, Book book) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public List<Book> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(Book entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Book> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Book> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Book> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Book> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Book> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Book> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Book getOne(UUID uuid) {
        return null;
    }

    @Override
    public Book getById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Book> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Book> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Book> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Book, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

 */