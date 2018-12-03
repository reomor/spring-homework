package task06.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Author {
    private final Integer id;
    private final String name;
    private String sername;
    private LocalDate dateOfBirth;
    private String biography;

    public Author(Integer id, String name, String sername, LocalDate dateOfBirth, String biography) {
        this.id = id;
        this.name = name;
        this.sername = sername;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
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
