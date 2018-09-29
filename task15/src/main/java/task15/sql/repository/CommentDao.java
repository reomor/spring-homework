package task15.sql.repository;

import task15.sql.domain.Comment;

import java.util.List;

public interface CommentDao {
    void create(Comment comment);

    void delete(int id);

    Comment getById(int id);

    List<Comment> getByBookId(int bookId);

    List<Comment> getAll();
}
