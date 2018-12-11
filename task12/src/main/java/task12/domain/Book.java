package task12.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Document
public class Book {
    @Id
    @ApiModelProperty(notes = "MongoDB generated id", example = "5nv56f92z0kvde2738fh5fvc")
    private String id;
    @ApiModelProperty(notes = "Book title", example = "The Complete Sherlock Holmes")
    private String title;
    @ApiModelProperty(notes = "Genre")
    private Genre genre;
    @ApiModelProperty(notes = "ISBN", example = "978-0553328257")
    private String isbn;
    @ApiModelProperty(notes = "Book description", example = "Volume I includes the early novel A Study in Scarlet...")
    private String description;
    @DBRef
    @ApiModelProperty(notes = "Book authors (References to authors)")
    private List<Author> authors;
    @ApiModelProperty(notes = "Book comments")
    private List<Comment> comments;

    public Book(String title, Genre genre, String isbn, String description, List<Author> authors, List<Comment> comments) {
        this(null, title, genre, isbn, description, authors, comments);
    }
}
