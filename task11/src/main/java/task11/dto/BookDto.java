package task11.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import task11.domain.Author;
import task11.domain.Book;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class BookDto {
    @ApiModelProperty(notes = "Book", example = "")
    private Book book;
    @ApiModelProperty(notes = "List of authors", example = "")
    private List<Author> authorList;
    @ApiModelProperty(notes = "List of author ids", example = "[\"5b77ff92f489de2738fa6c07\", \"5b77ff92f489de2738fa6ad3\"]")
    private List<String> authorIds;
}
