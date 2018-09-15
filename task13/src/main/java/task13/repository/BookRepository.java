package task13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task13.domain.Book;
import task13.domain.Comment;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
@Repository
public interface BookRepository extends MongoRepository<Book, String>, ExtendedBookRepository {

    @Override
    void setComment(String id, Comment comment);

    @Override
    List<Comment> getComments(String id);
}
