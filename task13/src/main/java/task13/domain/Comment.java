package task13.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Comment {
    @ApiModelProperty(notes = "Comment body", example = "This book is amazing")
    private String commentBody;
    @ApiModelProperty(notes = "Date (LocalDateTime)", example = "2018-08-26T20:10:51.536Z")
    private LocalDateTime date;
}
