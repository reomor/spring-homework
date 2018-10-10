package task17.service.console;

import task17.domain.Comment;

public interface CommentRepositoryConsoleService extends RepositoryConsoleService<Comment> {
    void getByBookId(int bookId);
}
