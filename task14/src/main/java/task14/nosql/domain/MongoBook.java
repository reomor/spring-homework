package task14.nosql.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Document
public class MongoBook {
    @Id
    private String id;
    private String title;
    private MongoGenre genre;
    private String isbn;
    private String description;
    @DBRef
    private List<MongoAuthor> authors;
    private List<MongoComment> comments;

    public MongoBook() {
        this.genre = new MongoGenre();
        this.authors = Collections.emptyList();
        this.comments = Collections.emptyList();
    }

    public MongoBook(String title, MongoGenre genre, String isbn, String description, List<MongoAuthor> authors, List<MongoComment> comments) {
        this(null, title, genre, isbn, description, authors, comments);
    }
}
