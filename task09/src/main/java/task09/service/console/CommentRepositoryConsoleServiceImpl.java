package task09.service.console;

import task09.domain.Comment;
import task09.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class CommentRepositoryConsoleServiceImpl {

    private Comment readComment(BufferedReader reader) throws IOException {
        System.out.println("Reading Comment object.\nEnter comment:");
        String comment = reader.readLine();
        return new Comment(comment, LocalDateTime.now());
    }

    private Comment updateComment(BufferedReader reader, Comment comment) throws IOException {
        if (comment == null) {
            throw new ConsoleReadException(Comment.class.getName() + " object is null");
        }
        System.out.println("Reading Comment object.\nEnter comment:");
        String commentBody = reader.readLine();
        if (!commentBody.isEmpty()) {
            comment.setBody(commentBody);
        }
        return comment;
    }
}
