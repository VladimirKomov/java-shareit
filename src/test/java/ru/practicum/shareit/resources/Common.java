package ru.practicum.shareit.resources;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Common {

    public static String getFile(String path) {
        File file = new File(path);
        try {
            return Files.readString(file.toPath(),
                    StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException("Error open file", e);
        }
    }
}
