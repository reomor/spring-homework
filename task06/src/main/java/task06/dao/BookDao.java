package task06.dao;

import task06.domain.Book;

import java.util.List;

public interface BookDao {

    int create(Book book);

    int update(Book book);

    boolean delete(int id);

    Book getById(int id);

    List<Book> getAll();

    List<Book> getByAuthorId(int authorId);
}
