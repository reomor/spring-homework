package task06.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private final Integer id;
    private final String name;
    private final String sername;
    private final LocalDate dateOfBirth;
    private final String biography;
    private List<Book> authorBooks;

    public Author(Integer id, String name, String sername, LocalDate dateOfBirth, String biography) {
        this(id, name, sername, dateOfBirth, biography, new ArrayList<>());
    }

    public Author(Integer id, String name, String sername, LocalDate dateOfBirth, String biography, List<Book> authorBooks) {
        this.id = id;
        this.name = name;
        this.sername = sername;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.authorBooks = authorBooks;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSername() {
        return sername;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBiography() {
        return biography;
    }

    public List<Book> getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(List<Book> authorBooks) {
        this.authorBooks = authorBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sername, dateOfBirth);
    }
}
