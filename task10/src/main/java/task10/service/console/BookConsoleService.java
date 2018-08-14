package task10.service.console;

import task10.domain.Book;

import java.io.BufferedReader;

public interface BookConsoleService extends ConsoleService<Book> {
    void setComment(BufferedReader reader, String id);
    void getComments(String id);
}
