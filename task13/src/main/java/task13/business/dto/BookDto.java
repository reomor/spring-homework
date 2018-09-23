package task13.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import task13.business.domain.Author;
import task13.business.domain.Book;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class BookDto {
    @ApiModelProperty(notes = "Book")
    private Book book;
    @ApiModelProperty(notes = "List of authors")
    private List<Author> authorList;
    @ApiModelProperty(notes = "List of author ids", example = "[\"5b77ff92f489de2738fa6c07\", \"5b77ff92f489de2738fa6ad3\"]")
    private List<String> authorIds;
}
