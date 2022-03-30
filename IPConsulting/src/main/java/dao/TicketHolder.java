package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TicketHolder {
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return "TicketHolder{" +
                "ticketList=" + tickets +
                '}';
    }
}
