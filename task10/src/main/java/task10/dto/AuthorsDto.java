package task10.dto;

import lombok.ToString;
import task10.domain.Author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
public class AuthorsDto {
    private List<Author> authorList;

    public AuthorsDto() {
        this.authorList = Collections.emptyList();
    }

    public AuthorsDto(List<Author> authorList) {
        this.authorList = new ArrayList<>(authorList);
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
