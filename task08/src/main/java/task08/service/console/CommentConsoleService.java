package task08.service.console;

import task08.domain.Comment;

import java.io.BufferedReader;

public interface CommentConsoleService {
    Comment create(BufferedReader reader);
}
