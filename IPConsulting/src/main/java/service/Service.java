package service;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

public interface Service<T> {
    String readFile(Path path);
    List<T> deserializedJsonObjectInArray(String data);
    String averageFlightTimeBetweenCities(List<T> ticketHolder) throws ParseException;
}
