package task09.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Author {
    @Id
    private String id;
    private String name;
    private String sername;
    private LocalDate dateOfBirth;
    private String biography;

    public Author(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography);
    }
}
