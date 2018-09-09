package task12.rest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task12.domain.Author;
import task12.repository.AuthorReactiveRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

public class RestAuthorTest extends AbstractRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    protected AuthorReactiveRepository repository;

    @Test
    public void givenAuthors_whenFindAllAuthors_thenStatusOk() {
        Author author1 = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        Author author2 = new Author("2", "Test2", "Sername2", LocalDate.now(), "biography2");

        given(repository.findAll()).willReturn(Flux.just(author1, author2));

        EntityExchangeResult<List<Author>> result = webTestClient.get()
                .uri("/rest/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Author.class)
                .returnResult();

        Objects.requireNonNull(result.getResponseBody()).forEach(System.out::println);
    }

    @Test
    public void givenAuthor_whenFindByIdAuthor_thenStatusOk() {
        Author author = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");

        given(repository.findById("1")).willReturn(Mono.just(author));

        EntityExchangeResult<Author> result = webTestClient.get()
                .uri("/rest/authors/" + author.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenAuthor_whenSaveAuthor_thenStatusOk() {
        Author authorNew = new Author(null, "Test1", "Sername1", LocalDate.now(), "biography1");
        Author authorSaved = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");

        given(repository.save(authorNew)).willReturn(Mono.just(authorSaved));

        EntityExchangeResult<Author> result = webTestClient.post()
                .uri("/rest/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(authorNew), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .consumeWith(response -> {
                    Assertions.assertThat(response.getResponseBody()).isEqualTo(authorSaved);
                })
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenUpdatedAuthor_whenUpdateAuthor_thenStatusOk() {
        LocalDate dateOfBirth = LocalDate.of(1991, Month.APRIL, 24);
        Author author = new Author("1", "Test1Updated", "Sername1", dateOfBirth, "biography1");
        Author authorUpdated = new Author("1", "Test1Updated", "Sername1", dateOfBirth, "biography1");

        given(repository.findById("1")).willReturn(Mono.just(author));
        given(repository.save(author)).willReturn(Mono.just(authorUpdated));

        EntityExchangeResult<Author> result = webTestClient.put()
                .uri("/rest/authors/" + author.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Author.class)
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isEqualTo(authorUpdated))
                .returnResult();

        System.out.println(result.getResponseBody());
    }

    @Test
    public void givenId_whenDeleteAuthor_thenStatusOk() {
        Author authorToDelete = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");

        given(repository.findById("1")).willReturn(Mono.just(authorToDelete));
        given(repository.delete(authorToDelete)).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/rest/authors/" + authorToDelete.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();
    }
}
