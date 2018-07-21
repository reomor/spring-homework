package task07.dao;

import task07.domain.Book;

import java.util.List;

public interface BookDao {

    int create(Book book);

    void update(Book book);

    boolean delete(int id);

    Book getById(int id);

    int getRelatedId(int bookId);

    List<Book> getAll();

    List<Book> getByAuthorId(int authorId);
}
