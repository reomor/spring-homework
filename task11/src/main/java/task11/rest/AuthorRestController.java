package task11.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import task11.domain.Author;
import task11.repository.AuthorRepository;

import java.util.List;

@Slf4j
@RestController
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // list all authors
    @GetMapping("/rest/authors")
    public ResponseEntity<List<Author>> getAllAuthors() throws Throwable {
        log.info("Get all authors by rest");
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    // list all authors
    @GetMapping("/rest/authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable String id) throws Throwable {
        log.info("Get author by id{} by rest", id);
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
