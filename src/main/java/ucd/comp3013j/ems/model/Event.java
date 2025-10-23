package ucd.comp3013j.ems.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    private int standingNumberAvailable;
    private int seatingNumberAvailable;
    private int premiumNumberAvailable;

    /*ManyToOne
    @JoinColumn(name = "organiser_id"*/
    @OneToOne
    private Organiser organiser;
    @ManyToOne
    private Venue venue;
//    @OneToMany(mappedBy = "event")
//    private List<Event> schedule;

//    @ManyToMany
//    @JoinTable(
//            name = "event_organizer",
//            joinColumns = @JoinColumn(name = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "organizer_id")
//    )
//    private List<Organiser> organizers;

    /*@Getter
    @ManyToOne
    @JoinColumn(name = "venue_id")


     */

    // 构造函数
    public Event(String name, String description, String imageUrl, LocalDate date, LocalTime startTime, LocalTime endTime,
                 int standingNumberAvailable, int seatingNumberAvailable, int premiumNumberAvailable, Venue v, Organiser o) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.standingNumberAvailable = standingNumberAvailable;
        this.seatingNumberAvailable = seatingNumberAvailable;
        this.premiumNumberAvailable = premiumNumberAvailable;
        this.organiser = o;
    }



    public Event getEvent(long eId) {
        // 实现获取Event的逻辑
        return this;
    }

    // 检查票数方法
    public boolean checkStandingNumberAvailable(int ticketNum) {
        return this.standingNumberAvailable >= ticketNum;
    }

    public boolean checkSeatingNumberAvailable(int ticketNum) {
        return this.seatingNumberAvailable >= ticketNum;
    }

    public boolean checkPremiumNumberAvailable(int ticketNum) {
        return this.premiumNumberAvailable >= ticketNum;
    }
}