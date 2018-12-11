package task08.service.console;

import task08.domain.Book;

import java.io.BufferedReader;

public interface BookConsoleService extends ConsoleService<Book> {
    void setComment(BufferedReader reader, String id);
    void getComments(String id);
}
