package task11.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import task11.domain.Author;
import task11.dto.AuthorDto;
import task11.exception.ObjectNotFound;
import task11.repository.AuthorRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorRestController(AuthorRepository authorRepository, ObjectMapper objectMapper) {
        this.authorRepository = authorRepository;
        this.objectMapper = objectMapper;
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
        Author author = checkIfExists(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // create new author
    @PostMapping(
            value = "/rest/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author author, HttpServletRequest request) {
        Author newAuthor = new Author("1", "Test1", "Sername1", LocalDate.now(), "biography1");
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    // update existing author
    @PostMapping("/rest/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        Author existingAuthor = checkIfExists(id);
        return new ResponseEntity<>(existingAuthor, HttpStatus.ACCEPTED);
    }

    // delete author
    public ResponseEntity<Author> deleteAuthor(@PathVariable String id) {
        Author author = checkIfExists(id);
        //authorRepository.deleteById(author.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Author checkIfExists(String id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new ObjectNotFound();
    }

    private String contactUrlHelper(Author author, HttpServletRequest request) {
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append(request.getRequestURL());
        resourcePath.append("/");
        resourcePath.append(author.getId());
        return resourcePath.toString();
    }
}
