package task11.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task11.domain.Comment;

public interface ExtendedBookReactiveRepository {

    Mono<Void> setComment(String id, Comment comment);

    Flux<Comment> getComments(String id);
}
