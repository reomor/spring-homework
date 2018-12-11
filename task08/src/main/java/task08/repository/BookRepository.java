package task08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import task08.domain.Book;
import task08.domain.Comment;
import task08.domain.Genre;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
public interface BookRepository extends MongoRepository<Book, String>, ExtendedBookRepository {

    @Override
    void setComment(String id, Comment comment);

    @Override
    List<Comment> getComments(String id);
}
