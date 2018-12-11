package task14.sql.repository;

import task14.sql.domain.RdbmsAuthor;

import java.util.List;

public interface AuthorDao {

    void create(RdbmsAuthor author);

    RdbmsAuthor update(RdbmsAuthor author);

    void delete(int id);

    RdbmsAuthor getById(int id);

    List<RdbmsAuthor> getAll();
}
