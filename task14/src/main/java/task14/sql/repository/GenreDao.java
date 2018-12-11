package task14.sql.repository;

import task14.sql.domain.RdbmsGenre;

import java.util.List;

public interface GenreDao {

    void create(RdbmsGenre genre);

    RdbmsGenre update(RdbmsGenre genre);

    void delete(int id);

    RdbmsGenre getById(int id);

    List<RdbmsGenre> getAll();
}
