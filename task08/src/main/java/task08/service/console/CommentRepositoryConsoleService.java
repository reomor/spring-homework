package task08.service.console;

import task08.domain.Comment;

public interface CommentRepositoryConsoleService extends RepositoryConsoleService<Comment> {
    void getByBookId(int bookId);
}
