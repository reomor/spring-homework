package task17.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task17.domain.Author;
import task17.dto.AuthorHateoasDto;
import task17.exception.ObjectNotFound;
import task17.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/hateoas/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<AuthorHateoasDto> getAuthor(@PathVariable Integer id) {
        log.info("Get author by id({}) by rest", id);
        Author author = checkIfExists(id);
        AuthorHateoasDto authorHateoasDto = AuthorHateoasDto.of(author);
        Link selfLink = linkTo(methodOn(AuthorRestController.class).getAuthor(id)).withSelfRel().withType("GET");
        authorHateoasDto.add(selfLink);
        authorHateoasDto.add(linkTo(methodOn(AuthorRestController.class).deleteAuthor(id))
                .withRel("delete_author")
                .withType("DELETE"));
        return new Resource<>(authorHateoasDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<AuthorHateoasDto> deleteAuthor(@PathVariable Integer id) {
        log.info("Delete author by id({}) by rest", id);
        Author author = checkIfExists(id);
        authorRepository.deleteById(author.getId());
        AuthorHateoasDto authorHateoasDto = AuthorHateoasDto.of(new Author());
        authorHateoasDto.add(linkTo(methodOn(AuthorRestController.class).getAllAuthors()).withRel("get_users").withType("GET"));
        return new Resource<>(authorHateoasDto);
    }

    @GetMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<AuthorHateoasDto> getAllAuthors() {
        log.info("Get all authors by rest");
        List<Author> all = authorRepository.findAll();
        List<AuthorHateoasDto> authorHateoasDtoList = all.stream()
                .map(author -> {
                    AuthorHateoasDto authorHateoasDto = AuthorHateoasDto.of(author);
                    Integer authorId = authorHateoasDto.getAuthorId();
                    authorHateoasDto.add(
                            linkTo(methodOn(AuthorRestController.class).getAuthor(authorId))
                                    .withRel("get_author")
                                    .withType("GET"));
                    authorHateoasDto.add(
                            linkTo(methodOn(AuthorRestController.class).deleteAuthor(authorId))
                                    .withRel("delete_author")
                                    .withType("DELETE"));
                    return authorHateoasDto;
                })
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(AuthorRestController.class).getAllAuthors())
                .withSelfRel()
                .withType("GET");
        return new Resources<>(authorHateoasDtoList, selfLink);
    }

    @PostMapping(value = "/hateoas/authors/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author authorNew) {
        log.info("Create new author({}) by rest", authorNew);
        authorNew.setId(null);
        Author author = authorRepository.save(authorNew);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/hateoas/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author authorNew) {
        log.info("Update author({}) by id({}) by rest", authorNew, id);
        checkIfExists(id);
        authorNew.setId(id);
        Author author = authorRepository.save(authorNew);
        return new ResponseEntity<>(author, HttpStatus.ACCEPTED);
    }

    private Author checkIfExists(Integer id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new ObjectNotFound();
    }
}
