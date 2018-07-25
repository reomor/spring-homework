package task07.service;

import task07.domain.Comment;

import java.util.List;

public interface CommentService {

    void create(Comment comment);

    void delete(Comment comment);

    List<Comment> getById(int bookId);

    List<Comment> getAll();
}
