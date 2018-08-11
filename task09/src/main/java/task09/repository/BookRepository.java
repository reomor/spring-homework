package task09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import task09.domain.Book;
import task09.domain.Comment;
import task09.domain.Genre;

import java.util.List;

// https://lishman.io/custom-spring-data-mongodb-repository-methods
public interface BookRepository extends MongoRepository<Book, String>, ExtendedBookRepository {
    @Override
    void setGenre(String id, Genre genre);

    @Override
    void setComment(String bookTitle, Comment comment);

    @Override
    List<Comment> getComments(String bookTitle);
}
