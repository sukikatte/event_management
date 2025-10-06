package ucd.comp3013j.ems.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Getter
    private String admission;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // constructor
    public Ticket(User u, Event e, TicketType ticketType) {
        this.user = u;
        this.event = e;
        this.ticketType = ticketType;
    }

}