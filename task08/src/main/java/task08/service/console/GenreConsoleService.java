package task08.service.console;

import task08.domain.Genre;

import java.io.BufferedReader;

public interface GenreConsoleService {
    Genre create(BufferedReader reader);
}
