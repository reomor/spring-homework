package task15.sql.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class RdbmsBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private RdbmsGenre genre;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "author_book",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<RdbmsAuthor> authors;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RdbmsComment> comments;

    public RdbmsBook(String title, RdbmsGenre genre, String isbn, String description, List<RdbmsAuthor> authors, List<RdbmsComment> comments) {
        this(null, title, genre, isbn, description, authors == null ? new ArrayList<>() : authors, comments == null ? new ArrayList<>() : comments);
    }

    public RdbmsBook(Integer id, String title, RdbmsGenre genre, String isbn, String description, List<RdbmsAuthor> authors, List<RdbmsComment> comments) {
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.description = description;
        this.authors = authors;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdbmsBook book = (RdbmsBook) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("RdbmsBook{id=").append(id)
                .append(", title=").append(title)
                .append(", genre=").append(genre)
                .append(", isbn=").append(isbn)
                .append(", description=").append(description);
        if (authors != null && authors.size() > 0) {
            toString.append("\n");
            for (RdbmsAuthor author : authors) {
                toString.append(author).append("\n");
            }
        }
        toString.append("}");
        return toString.toString();
    }
}
