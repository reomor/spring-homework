package task11.dto;

import lombok.*;
import task11.domain.Author;
import task11.domain.Book;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class BookDto {
    private Book book;
    private List<Author> authorList;
    private List<String> authorIds;
}
