package task06.dao;

import task06.domain.Author;

import java.util.List;

public interface AuthorDao {

    void create(Author author);

    Author update(Author author);

    void delete(int id);

    Author getById(int id);

    List<Author> getAll();
}
