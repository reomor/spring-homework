package task06.service;

import task06.domain.Comment;
import task06.service.console.DaoConsoleService;


public interface CommentService extends DaoConsoleService<Comment> {
    void getByBookId(int bookId);
}
