package task07.service.console;

import task07.domain.Comment;

public interface CommentRepositoryConsoleService extends RepositoryConsoleService<Comment> {
    void getByBookId(int bookId);
}
