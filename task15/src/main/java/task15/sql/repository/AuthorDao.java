package task15.sql.repository;

import task15.sql.domain.Author;

import java.util.List;

public interface AuthorDao {

    void create(Author author);

    Author update(Author author);

    void delete(int id);

    Author getById(int id);

    List<Author> getAll();
}
