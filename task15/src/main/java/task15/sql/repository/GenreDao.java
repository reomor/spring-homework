package task15.sql.repository;

import task15.sql.domain.Genre;

import java.util.List;

public interface GenreDao {

    void create(Genre genre);

    Genre update(Genre genre);

    void delete(int id);

    Genre getById(int id);

    List<Genre> getAll();
}
