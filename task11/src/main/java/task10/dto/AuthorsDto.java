package task11.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import task11.domain.Author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
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
