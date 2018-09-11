package task12.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task12.domain.Book;
import task12.domain.Comment;

@Component
public class BookReactiveRepositoryImpl implements ExtendedBookReactiveRepository {

    private final ReactiveMongoOperations operations;

    @Autowired
    public BookReactiveRepositoryImpl(ReactiveMongoOperations operations) {
        this.operations = operations;
    }

    @Override
    public Mono<Void> setComment(String id, Comment comment) {
        operations.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().push("comments", comment), Book.class);
        return Mono.empty();
    }

    @Override
    public Flux<Comment> getComments(String id) {
        Mono<Book> bookMono = operations.findOne(Query.query(Criteria.where("_id").is(id)), Book.class);
        return bookMono.flatMapIterable(Book::getComments);
    }
}
