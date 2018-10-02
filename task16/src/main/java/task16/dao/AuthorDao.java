package task16.dao;

import task16.domain.Author;

import java.util.List;

public interface AuthorDao {

    int create(Author author);

    int update(Author author);

    boolean delete(int id);

    Author getById(int id);

    List<Author> getAll();

    List<Author> getByBookId(int bookId);
}
