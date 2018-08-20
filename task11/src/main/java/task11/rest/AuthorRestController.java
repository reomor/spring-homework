package task11.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import task11.domain.Author;
import task11.repository.AuthorRepository;

import java.util.List;

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
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }
}
