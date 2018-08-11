package task09.service.console;

import task09.domain.Genre;

import java.io.BufferedReader;

public interface GenreConsoleService {
    Genre create(BufferedReader reader);
}
