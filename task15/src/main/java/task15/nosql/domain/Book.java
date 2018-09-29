package task15.nosql.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
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
    private List<MongoAuthor> authors;
    private List<Comment> comments;

    public Book() {
        this.genre = new Genre();
        this.authors = Collections.emptyList();
        this.comments = Collections.emptyList();
    }

    public Book(String title, Genre genre, String isbn, String description, List<MongoAuthor> authors, List<Comment> comments) {
        this(null, title, genre, isbn, description, authors, comments);
    }
}
