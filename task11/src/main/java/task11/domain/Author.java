package task11.domain;

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
    @ApiModelProperty(notes = "Author name", example = "Alex")
    private String name;
    @ApiModelProperty(notes = "Author sername", example = "Alexeev")
    private String sername;
    @ApiModelProperty(notes = "Author date of birth", example = "2018-08-18")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    @ApiModelProperty(notes = "Author biography", example = "The life of Alex Alexeev")
    private String biography;

    public Author(String name, String sername, LocalDate dateOfBirth, String biography) {
        this(null, name, sername, dateOfBirth, biography);
    }
}
