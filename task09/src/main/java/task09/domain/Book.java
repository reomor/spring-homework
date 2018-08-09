package task09.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {
    @Id
    private String id;
    private String title;
    private Genre genre;
    private String isbn;
    private String description;
    @DBRef
    private List<Author> authors;
    private List<Comment> comments;

    public Book(String title, Genre genre, String isbn, String description, List<Author> authors, List<Comment> comments) {
        this(null, title, genre, isbn, description, authors, comments);
    }
}
