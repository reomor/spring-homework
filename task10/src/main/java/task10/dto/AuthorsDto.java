package task10.dto;

import task10.domain.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorsDto {
    private List<Author> authorList;

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
