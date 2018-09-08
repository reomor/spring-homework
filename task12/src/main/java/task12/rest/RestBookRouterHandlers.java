package task12.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import task12.domain.Book;
import task12.domain.Comment;
import task12.repository.BookReactiveRepository;


@Component
public class RestBookRouterHandlers {

    private final BookReactiveRepository repository;

    @Autowired
    public RestBookRouterHandlers(BookReactiveRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Book.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(repository.findById(request.pathVariable("id")), Book.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Book> bookMono = request.body(BodyExtractors.toMono(Book.class)).flatMap(repository::save);
        return ServerResponse.ok().body(bookMono, Book.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Book> bookMono = repository.findById(request.pathVariable("id"))
                .flatMap(book -> request.body(BodyExtractors.toMono(Book.class))
                        .flatMap(bookInRequestBody -> {
                            book.setTitle(bookInRequestBody.getTitle());
                            book.setDescription(bookInRequestBody.getDescription());
                            book.setGenre(bookInRequestBody.getGenre());
                            book.setIsbn(bookInRequestBody.getIsbn());
                            book.setAuthors(bookInRequestBody.getAuthors());
                            return repository.save(book);
                        }));
        return ServerResponse.ok().body(bookMono, Book.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        repository.findById(request.pathVariable("id")).flatMap(repository::delete).subscribe();
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> addComment(ServerRequest request) {
        repository.findById(request.pathVariable("id"))
                .flatMap(book -> request.body(BodyExtractors.toMono(Comment.class))
                        .flatMap(comment -> repository.setComment(book.getId(), comment)));
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> findComments(ServerRequest request) {
        return ServerResponse.ok().body(repository.getComments(request.pathVariable("id")), Comment.class);
    }
}
