package task10.domain;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "MongoDB generated id", example = "5b77ff92f489de2738fa6c07")
    private String id;
    @ApiModelProperty(notes = "Author name", example = "Sir Arthur")
    private String name;
    @ApiModelProperty(notes = "Author sername", example = "Conan Doyle")
    private String sername;
    @ApiModelProperty(notes = "Author date of birth (LocalDate)", example = "1859-05-22")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    @ApiModelProperty(notes = "Author biography", example = "was a British writer best known for his detective fiction featuring the character Sherlock Holmes...")
    private String biography;

    public Author(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography);
    }
}
