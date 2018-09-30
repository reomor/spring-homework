package task15.nosql.repository;

import task15.nosql.domain.MongoComment;

import java.util.List;

// https://stackoverflow.com/questions/10905508/is-the-mongodb-push-operator-the-way-to-add-an-embedded-document
public interface ExtendedBookRepository {
    void setComment(String id, MongoComment comment);
    List<MongoComment> getComments(String id);
}
