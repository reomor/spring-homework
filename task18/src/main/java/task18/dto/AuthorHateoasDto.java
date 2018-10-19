package task18.dto;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import task18.domain.Author;

import java.time.LocalDate;

@Getter
// https://stackoverflow.com/questions/25858698/spring-hateoas-embedded-resource-support
@Relation(value = "author", collectionRelation = "authors")
public class AuthorHateoasDto extends ResourceSupport {

    private Integer authorId;
    private String authorName;
    private String authorSername;
    private LocalDate authorDateOfBirth;
    private String authorBiography;

    public AuthorHateoasDto(Author author) {
        this.authorId = author.getId();
        this.authorName = author.getName();
        this.authorSername = author.getSername();
        this.authorDateOfBirth = author.getDateOfBirth();
        this.authorBiography = author.getBiography();
    }

    public static AuthorHateoasDto of(Author author) {
        return new AuthorHateoasDto(author);
    }
}
