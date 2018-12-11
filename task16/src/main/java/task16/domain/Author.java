package task16.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@ToString
@NoArgsConstructor
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "sername")
    private String sername;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "biography")
    private String biography;

    public Author(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography);
    }

    public Author(Integer id, String name, String sername, LocalDate dateOfBirth, String biography) {
        this.id = id;
        this.name = name;
        this.sername = sername;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(sername, author.sername) &&
                Objects.equals(dateOfBirth, author.dateOfBirth) &&
                Objects.equals(biography, author.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sername, dateOfBirth);
    }
}
