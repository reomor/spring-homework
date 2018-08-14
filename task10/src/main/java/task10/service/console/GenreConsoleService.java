package task10.service.console;

import task10.domain.Genre;

import java.io.BufferedReader;

public interface GenreConsoleService {
    Genre create(BufferedReader reader);
}
