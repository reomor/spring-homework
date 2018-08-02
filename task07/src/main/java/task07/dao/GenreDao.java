package task07.dao;

import task07.domain.Genre;

import java.util.List;

public interface GenreDao {

    void create(Genre genre);

    Genre update(Genre genre);

    void delete(int id);

    Genre getById(int id);

    List<Genre> getAll();
}
