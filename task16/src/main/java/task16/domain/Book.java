package task16.domain;

import java.util.Objects;

public class Book {

    private Integer id;
    private String title;
    private Integer idGenre;
    private String isbn;
    private String description;
    private Author author;

    public Book(Integer id, String title, Integer idGenre, String isbn, String description) {
        this(id, title, idGenre, isbn, description, null);
    }

    public Book(Integer id, String title, Integer idGenre, String isbn, String description, Author author) {
        this.id = id;
        this.title = title;
        this.idGenre = idGenre;
        this.isbn = isbn;
        this.description = description;
        this.author = author;
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

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(idGenre, book.idGenre) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, idGenre, isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idGenre=" + idGenre +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }
}
