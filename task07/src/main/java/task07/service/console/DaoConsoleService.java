package task07.service.console;

import java.io.BufferedReader;
import java.util.List;

public interface DaoConsoleService<T> {
    void create(BufferedReader reader);

    T update(BufferedReader reader);

    void delete(int objectId);

    T getById(int id);

   List<T> getAll();

    default <T> void printList(List<T> list) {
        System.out.println("=== list begin ===");
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println("=== list end ===");
    }
}
