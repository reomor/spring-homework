package task08.service;

import task08.domain.Comment;
import task08.service.console.DaoConsoleService;


public interface CommentService extends DaoConsoleService<Comment> {
    void getByBookId(int bookId);
}
