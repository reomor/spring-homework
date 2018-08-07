package task09.service.console;

import task09.domain.Comment;

public interface CommentRepositoryConsoleService extends RepositoryConsoleService<Comment> {
    void getByBookId(int bookId);
}
