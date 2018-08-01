package task08.dao;

import task08.domain.Book;

import java.util.List;

public interface BookDao {

    void create(Book book);

    Book update(Book book);

    void delete(int id);

    Book getById(int id);

    List<Book> getAll();
}
