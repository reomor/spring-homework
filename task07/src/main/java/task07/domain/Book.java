package task07.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_book",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<Author> authors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

    protected Book() {
    }

    public Book(Integer id, String title, Genre genre, String isbn, String description) {
        this(id, title, genre, isbn, description, new ArrayList<>(), new ArrayList<>());
    }

    public Book(Integer id, String title, Genre genre, String isbn, String description, List<Author> authors, List<Comment> comments) {
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.description = description;
        this.authors = authors;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
    // https://hellokoding.com/jpa-one-to-many-relationship-mapping-example-with-spring-boot-maven-and-mysql/
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Book{id=").append(id)
                .append(", title=").append(title)
                .append(", genre=").append(genre)
                .append(", isbn=").append(isbn)
                .append(", description=").append(description);
        if (authors != null && authors.size() > 0) {
            toString.append("\n");
            for (Author author : authors) {
                toString.append(author).append("\n");
            }
        }
        toString.append("}");
        return toString.toString();
    }
}
