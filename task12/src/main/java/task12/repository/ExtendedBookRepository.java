package task11.repository;

import task11.domain.Comment;
import task11.domain.Genre;

import java.util.List;

// https://stackoverflow.com/questions/10905508/is-the-mongodb-push-operator-the-way-to-add-an-embedded-document
public interface ExtendedBookRepository {
    void setComment(String id, Comment comment);
    List<Comment> getComments(String id);
}
