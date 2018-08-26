package task11.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task11.domain.Author;
import task11.exception.ObjectNotFound;
import task11.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

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

    // get author by id
    @GetMapping("/rest/authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable String id) throws Throwable {
        log.info("Get author by id({}) by rest", id);
        Author author = checkIfExists(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // create new author
    @PostMapping(
            value = "/rest/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author authorNew) {
        log.info("Create new author({}) by rest", authorNew);
        authorNew.setId(null);
        Author author = authorRepository.save(authorNew);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    // update existing author
    @PutMapping("/rest/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author authorNew) {
        log.info("Update author({}) by id({}) by rest", id, authorNew);
        checkIfExists(id);
        authorNew.setId(id);
        Author author = authorRepository.save(authorNew);
        return new ResponseEntity<>(author, HttpStatus.ACCEPTED);
    }

    // delete author
    @DeleteMapping("/rest/authors/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable String id) {
        log.info("Delete author by id({}) by rest", id);
        Author author = checkIfExists(id);
        authorRepository.deleteById(author.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Author checkIfExists(String id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new ObjectNotFound();
    }
}
