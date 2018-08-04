package task08.service.console;

import task08.domain.Comment;
import task08.service.console.DaoConsoleService;


public interface CommentConsoleService extends DaoConsoleService<Comment> {
    void getByBookId(int bookId);
}
