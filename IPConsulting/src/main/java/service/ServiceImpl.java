package service;

import com.google.common.io.CharStreams;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dao.Ticket;
import dao.TicketHolder;
import exceptions.AverageFlightException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiceImpl implements Service<TicketHolder> {
    @Override
    public String readFile(Path path) {
        String data = null;
        try {
            FileReader reader = new FileReader(path.toString());
            data = CharStreams.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<TicketHolder> deserializedJsonObjectInArray(String data) {
        Gson gson = new Gson();

        List<TicketHolder> ticketContainer = new ArrayList<>();
        try {
            Type foundListType = new TypeToken<ArrayList<TicketHolder>>() {
            }.getType();

            ticketContainer = gson.fromJson(data, foundListType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketContainer;
    }

    @Override
    public String averageFlightTimeBetweenCities(List<TicketHolder> ticketContainer) {
        String departure_time;
        String arrival_time;

        long difference = 0;
        int count = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        try {
            if (!ticketContainer.isEmpty()) {
                for (; count < ticketContainer.get(0).getTickets().size(); count++) {
                    departure_time = ticketContainer.get(0).getTickets().get(count).getDeparture_time();
                    arrival_time = ticketContainer.get(0).getTickets().get(count).getArrival_time();

                    Date dateDepartureTime = sdf.parse(departure_time);
                    Date dateArrivalTime = sdf.parse(arrival_time);

                    difference += dateArrivalTime.getTime() - dateDepartureTime.getTime();
                }

                long avg = difference / count;

                calendar.setTimeInMillis(avg);
            } else {
                throw new AverageFlightException("Container is empty!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }

    public Long getPercentile(List<Long> flyingTime, double percentile) {
        Collections.sort(flyingTime);
        int index = (int) Math.ceil(percentile / 100.0 * flyingTime.size());
        return flyingTime.get(index - 1);
    }

    public List<Long> seedAnArray(Path path) {
        List<Long> allDifferenceFlyingTime = new ArrayList<>();

        String departure_time;
        String arrival_time;

        long difference;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        List<TicketHolder> ticketContainer = deserializedJsonObjectInArray(readFile(path));
        try {
            if (!ticketContainer.isEmpty()) {
                for (Ticket ticket : ticketContainer.get(0).getTickets()) {
                    departure_time = ticket.getDeparture_time();
                    arrival_time = ticket.getArrival_time();

                    Date dateDepartureTime = sdf.parse(departure_time);
                    Date dateArrivalTime = sdf.parse(arrival_time);

                    difference = dateArrivalTime.getTime() - dateDepartureTime.getTime();

                    allDifferenceFlyingTime.add(difference);
                }
            } else {
                throw new AverageFlightException("Array is empty!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return allDifferenceFlyingTime;
    }
}
