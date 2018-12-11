package task11.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import task11.domain.Author;
import task11.repository.AuthorReactiveRepository;

@Component
public class RestAuthorRouterHandlers {

    private final AuthorReactiveRepository repository;

    @Autowired
    public RestAuthorRouterHandlers(AuthorReactiveRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().body(repository.findById(request.pathVariable("id")), Author.class);
    }

    // https://github.com/ibercode/spring-webflux-mono-flux-router-handler/
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Author> authorMono = request.body(BodyExtractors.toMono(Author.class)).flatMap(repository::save);
        return ServerResponse.ok().body(authorMono, Author.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Author> authorMono = repository.findById(request.pathVariable("id"))
                .flatMap(author -> request.body(BodyExtractors.toMono(Author.class))
                        .flatMap(authorInRequestBody -> {
                            author.setName(authorInRequestBody.getName());
                            author.setSername(authorInRequestBody.getSername());
                            author.setBiography(authorInRequestBody.getBiography());
                            author.setDateOfBirth(authorInRequestBody.getDateOfBirth());
                            return repository.save(author);
                        }));
        return ServerResponse.ok().body(authorMono, Author.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        repository.findById(request.pathVariable("id")).flatMap(repository::delete).subscribe();
        return ServerResponse.ok().build();
    }
}
