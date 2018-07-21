package task07.dao;

import task07.domain.Author;

import java.util.List;

public interface AuthorDao {

    void create(Author author);

    Author update(Author author);

    void delete(int id);

    Author getById(int id);

    List<Author> getAll();

    List<Author> getByBookId(int bookId);
}
