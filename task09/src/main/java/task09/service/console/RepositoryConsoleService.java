package task09.service.console;

import java.io.BufferedReader;
import java.util.List;

public interface RepositoryConsoleService<T> {

    void create(BufferedReader reader);

    T update(BufferedReader reader);

    void delete(int objectId);

    void getById(int id);

    void getAll();

    default <T> void printObject(T object) {
        System.out.println("=== object begin ===");
        System.out.println(object);
        System.out.println("=== object end ===");
    }

    default <T> void printList(List<T> list) {
        System.out.println("=== list begin ===");
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println("=== list end ===");
    }
}
