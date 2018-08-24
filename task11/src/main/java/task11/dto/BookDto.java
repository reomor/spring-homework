package task11.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import task11.domain.Author;
import task11.domain.Book;

import java.util.List;

@Value
@ToString
@AllArgsConstructor
public class BookDto {
    private Book book;
    private List<Author> authorList;
    private List<String> authorIds;
}
