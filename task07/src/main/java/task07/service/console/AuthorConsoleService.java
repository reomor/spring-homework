package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.domain.Author;

import java.util.List;

@Service
public class AuthorConsoleService implements DaoConsoleService<Author> {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorConsoleService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void create() {

    }

    @Override
    public Author update(Author object) {
        return null;
    }

    @Override
    public void delete(int objectId) {

    }

    @Override
    public Author getById(int id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }
}
