package task15.sql.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public class RdbmsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "body")
    private String body;

    @Column(name = "date")
    private LocalDateTime date;

    public RdbmsComment(String body, LocalDateTime date) {
        this(null, body, date);
    }

    public RdbmsComment(Integer id, String body, LocalDateTime date) {
        this.id = id;
        this.body = body;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdbmsComment comment = (RdbmsComment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(body, comment.body) &&
                Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, date);
    }

    @Override
    public String toString() {
        return "RdbmsComment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
