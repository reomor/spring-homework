package task12.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Genre {
    @ApiModelProperty(notes = "Genre name", example = "Detective")
    private String genreName;
    @ApiModelProperty(notes = "Genre description", example = "Detective fiction is a subgenre of crime fiction and mystery fiction in which an investigator or a detective—either professional, amateur or retired—investigates a crime, often murder.")
    private String genreDescription;
}
