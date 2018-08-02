package task07.service;

import task07.domain.Comment;
import task07.service.console.DaoConsoleService;


public interface CommentService extends DaoConsoleService<Comment> {
    void getByBookId(int bookId);
}
