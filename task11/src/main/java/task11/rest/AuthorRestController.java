package task11.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "author controller", description = "Operations to manage authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // list all authors
    @GetMapping("/rest/authors")
    @ApiOperation(value = "View a list of all authors", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved authors list"),
            @ApiResponse(code = 401, message = "Not authorized to do operation"),
            @ApiResponse(code = 403, message = "Access to author is forbidden"),
            @ApiResponse(code = 404, message = "Author is not found")
    })
    public ResponseEntity<List<Author>> getAllAuthors() {
        log.info("Get all authors by rest");
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    // get author by id
    @GetMapping("/rest/authors/{id}")
    @ApiOperation(value = "Get author by id", response = Author.class)
    public ResponseEntity<Author> getAuthor(@PathVariable String id) {
        log.info("Get author by id({}) by rest", id);
        Author author = checkIfExists(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // create new author
    @PostMapping(value = "/rest/authors", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Post new author", response = Author.class)
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author author) {
        log.info("Create new author({}) by rest", author);
        author.setId(null);
        Author authorSaved = authorRepository.save(author);
        return new ResponseEntity<>(authorSaved, HttpStatus.CREATED);
    }

    // update existing author
    @PutMapping("/rest/authors/{id}")
    @ApiOperation(value = "Update author by id (demonstrates PUT)", response = Author.class)
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        log.info("Update author({}) by id({}) by rest", id, author);
        checkIfExists(id);
        author.setId(id);
        Author authorSaved = authorRepository.save(author);
        return new ResponseEntity<>(authorSaved, HttpStatus.ACCEPTED);
    }

    // delete author
    @DeleteMapping("/rest/authors/{id}")
    @ApiOperation(value = "Deleting author by id")
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
