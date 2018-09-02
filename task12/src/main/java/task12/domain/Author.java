package task12.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

// lombok bug, order matters :)
// https://github.com/rzwitserloot/lombok/issues/1703
@NoArgsConstructor
@Data
@AllArgsConstructor
@Document
public class Author implements Serializable {
    @Id
    private String id;
    private String name;
    private String sername;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String biography;

    public Author(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography);
    }
}
