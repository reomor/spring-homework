package task14.nosql.repository;

import task14.nosql.domain.MongoComment;

import java.util.List;

// https://stackoverflow.com/questions/10905508/is-the-mongodb-push-operator-the-way-to-add-an-embedded-document
public interface ExtendedBookRepository {
    void setComment(String id, MongoComment comment);
    List<MongoComment> getComments(String id);
}
