package task14.sql.repository;

import task14.sql.domain.RdbmsComment;

import java.util.List;

public interface CommentDao {
    void create(RdbmsComment comment);

    void delete(int id);

    RdbmsComment getById(int id);

    List<RdbmsComment> getByBookId(int bookId);

    List<RdbmsComment> getAll();
}
