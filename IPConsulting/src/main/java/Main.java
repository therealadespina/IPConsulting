import dao.TicketHolder;
import service.ServiceImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Main {
    public static final Path path = Paths.get("src/main/resources/tickets.json");

    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();

        List<TicketHolder> ticketHolders = service.deserializedJsonObjectInArray(
                service.readFile(path));

        System.out.println("Среднее время полета между городами "
                + ticketHolders.get(0).getTickets().get(0).getDestination_name()
                + " и "
                + ticketHolders.get(0).getTickets().get(0).getOrigin_name() + ": "
                + service.averageFlightTimeBetweenCities(ticketHolders));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();

        long time = service.getPercentile(service.seedAnArray(path), 90);
        calendar.setTimeInMillis(time);

        System.out.println("90-й процентиль времени полета между городами "
                + ticketHolders.get(0).getTickets().get(0).getDestination_name()
                + " и "
                + ticketHolders.get(0).getTickets().get(0).getOrigin_name() + ": "
                + sdf.format(calendar.getTime()));


    }
}
