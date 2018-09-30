package task15.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task15.nosql.domain.MongoBook;
import task15.nosql.domain.MongoComment;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
public interface BookRepository extends MongoRepository<MongoBook, String>, ExtendedBookRepository {

    @Override
    void setComment(String id, MongoComment comment);

    @Override
    List<MongoComment> getComments(String id);
}
