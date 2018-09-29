package task15.sql.repository;

import task15.sql.domain.RdbmsAuthor;

import java.util.List;

public interface AuthorDao {

    void create(RdbmsAuthor author);

    RdbmsAuthor update(RdbmsAuthor author);

    void delete(int id);

    RdbmsAuthor getById(int id);

    List<RdbmsAuthor> getAll();
}
