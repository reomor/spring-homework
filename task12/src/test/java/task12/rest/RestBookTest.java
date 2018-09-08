package task12.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import task12.repository.BookReactiveRepository;

public class RestBookTest extends AbstractRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    protected BookReactiveRepository repository;
}
