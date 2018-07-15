package task06.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private final Integer id;
    private final String title;
    private final Integer idGenre;
    private final String isbn;
    private final String description;
    private List<String> authors;

    public Book(Integer id, String title, Integer idGenre, String isbn, String description) {
        this(id, title, idGenre, isbn, description, new ArrayList<>());
    }

    public Book(Integer id, String title, Integer idGenre, String isbn, String description, List<String> authors) {
        this.id = id;
        this.title = title;
        this.idGenre = idGenre;
        this.isbn = isbn;
        this.description = description;
        this.authors = authors;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors.addAll(authors);
    }
}
