package task07.service.console;

import java.util.List;

public interface DaoConsoleService<T> {
    void create();

    T update(T object);

    void delete(int objectId);

    T getById(int id);

   List<T> getAll();
}
