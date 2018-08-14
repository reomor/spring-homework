package task10.service.console;

import java.io.BufferedReader;
import java.util.List;

public interface ConsoleService<T> extends AbstractConsoleService {
    void create(BufferedReader reader);
    void update(BufferedReader reader);
    void delete(String objectId);
    void getById(String objectId);
    void getAll();
}
