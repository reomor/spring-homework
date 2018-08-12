package task09.service.console;

import task09.domain.Comment;

import java.io.BufferedReader;

public interface CommentConsoleService {
    Comment create(BufferedReader reader);
}
