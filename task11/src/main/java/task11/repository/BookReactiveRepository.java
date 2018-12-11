package task11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task11.domain.Book;
import task11.domain.Comment;

// https://dzone.com/articles/spring-data-mongodb-with-reactive-mongodb
public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String>, ExtendedBookReactiveRepository {
    @Override
    Mono<Void> setComment(String id, Comment comment);

    @Override
    Flux<Comment> getComments(String id);
}
