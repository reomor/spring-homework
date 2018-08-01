package task08.dao;

import task08.domain.Comment;

import java.util.List;

public interface CommentDao {
    void create(Comment comment);

    void delete(int id);

    Comment getById(int id);

    List<Comment> getByBookId(int bookId);

    List<Comment> getAll();
}
