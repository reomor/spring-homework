package task15.sql.repository;

import task15.sql.domain.RdbmsBook;

import java.util.List;

public interface BookDao {

    void create(RdbmsBook book);

    RdbmsBook update(RdbmsBook book);

    void delete(int id);

    RdbmsBook getById(int id);

    List<RdbmsBook> getAll();
}
