package task15.sql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class RdbmsAuthor {

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public RdbmsAuthor(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography, null);
    }

    public RdbmsAuthor(Integer id, String name, String sername, LocalDate dateOfBirth, String biography, List<Book> books) {
        this.id = id;
        this.name = name;
        this.sername = sername;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        if (books != null) {
            this.books.addAll(books);
        }
    }

    @Override
    public String toString() {
        return "RdbmsAuthor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sername='" + sername + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", biography='" + biography + '\'' +
                '}';
    }
}
