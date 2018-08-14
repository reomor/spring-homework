package task10.service.console;

import task10.domain.Comment;

import java.io.BufferedReader;

public interface CommentConsoleService {
    Comment create(BufferedReader reader);
}
