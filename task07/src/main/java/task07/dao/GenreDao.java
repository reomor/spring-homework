package task07.dao;

import task07.domain.Genre;

import java.util.List;

public interface GenreDao {

    int create(Genre genre);

    int update(Genre genre);

    boolean delete(int id);

    Genre getById(int id);

    List<Genre> getAll();
}
