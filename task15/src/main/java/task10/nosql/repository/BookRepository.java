package task10.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task10.nosql.domain.Book;
import task10.nosql.domain.Comment;
import task10.nosql.domain.Genre;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
public interface BookRepository extends MongoRepository<Book, String>, ExtendedBookRepository {

    @Override
    void setComment(String id, Comment comment);

    @Override
    List<Comment> getComments(String id);
}
