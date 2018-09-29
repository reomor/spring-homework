package task15.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task15.nosql.domain.Book;
import task15.nosql.domain.Comment;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
public interface BookRepository extends MongoRepository<Book, String>, ExtendedBookRepository {

    @Override
    void setComment(String id, Comment comment);

    @Override
    List<Comment> getComments(String id);
}
