package task06.dao;

import task06.domain.Author;

import java.util.List;

public interface AuthorDao {

    int create(Author author);

    int update(Author author);

    boolean delete(int id);

    Author getById(int id);

    List<Author> getAll();
}
