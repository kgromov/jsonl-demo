package org.kgromov;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

public class App {

    static void main() throws Exception {


        Callable<List<Country>> callable2 = () -> {
            JsonMapper jsonMapper = JsonMapper.builder().build();
            Path countriesJsonl = Paths.get(App.class.getClassLoader().getResource("countries.jsonl").toURI());
            return Files.readAllLines(countriesJsonl).stream()
                    .map(line -> jsonMapper.readValue(line, Country.class))
                    .toList();
        };
        logDuration("Files#readAllLines", callable2);

        Callable<List<Country>> callable1 = () -> {
            JsonMapper jsonMapper = JsonMapper.builder().build();
            Path countriesJsonl = Paths.get(App.class.getClassLoader().getResource("countries.jsonl").toURI());
            try (var lines = Files.lines(countriesJsonl)) {
                return lines
                        .map(line -> jsonMapper.readValue(line, Country.class))
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        logDuration("Files#lines", callable1);
        Callable<List<Country>> fromJson = () -> {
            JsonMapper jsonMapper = JsonMapper.builder().build();
            try(InputStream inputStream = App.class.getClassLoader().getResource("countries.json").openStream()) {
                return jsonMapper.readValue(inputStream, new TypeReference<>() {});
            }
        };
        logDuration("JsonMapper#readValue", fromJson);
    }

    private static void logDuration(String message, Callable<List<Country>> countries) throws Exception {
        long start = System.nanoTime();
        var result = countries.call();
        long millis = Duration.ofNanos(System.nanoTime() - start).toMillis();
        System.out.printf("%nTime to map %d countries by %s =  %d ms%n", result.size(), message, millis);
    }
}
