package task06.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private Integer id;
    private String name;
    private String sername;
    private LocalDate dateOfBirth;
    private String biography;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(sername, author.sername) &&
                Objects.equals(dateOfBirth, author.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sername, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sername='" + sername + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", biography='" + biography + '\'' +
                ", authorBooks=" + authorBooks +
                '}';
    }
}
