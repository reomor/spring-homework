package task12.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import task12.domain.Author;
import task12.repository.AuthorReactiveRepository;

@Component
public class RestAuthorRouterHandlers {

    private final AuthorReactiveRepository repository;

    @Autowired
    public RestAuthorRouterHandlers(AuthorReactiveRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(repository.findById(request.pathVariable("id")), Author.class);
    }
    // https://github.com/ibercode/spring-webflux-mono-flux-router-handler/
    // save
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Author> authorMono = request.body(BodyExtractors.toMono(Author.class)).flatMap(repository::save);
        return ServerResponse.ok().body(authorMono, Author.class);
    }
    // update
    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Author> authorMono = repository.findById(request.pathVariable("id")).flatMap(author -> {
            Author authorUpdated = request.body(BodyExtractors.toMono(Author.class)).block();
            author.setName(authorUpdated.getName());
            author.setSername(authorUpdated.getSername());
            author.setBiography(authorUpdated.getBiography());
            author.setDateOfBirth(authorUpdated.getDateOfBirth());
            return repository.save(author);
        });
        return ServerResponse.ok().body(authorMono, Author.class);
    }
    // delete
    public Mono<ServerResponse> delete(ServerRequest request) {
        repository.findById(request.pathVariable("id")).flatMap(repository::delete).subscribe();
        return ServerResponse.ok().build();
    }
}
