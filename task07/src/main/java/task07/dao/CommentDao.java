package task07.dao;

import task07.domain.Comment;

import java.util.List;

public interface CommentDao {
    void create(Comment comment);

    void delete(int id);

    Comment getById(int id);

    List<Comment> getByBookId(int bookId);

    List<Comment> getAll();
}
