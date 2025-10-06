package ucd.comp3013j.ems.model.dto;

import lombok.Getter;
import lombok.Setter;
import ucd.comp3013j.ems.model.Venue;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ModifyEventDTO {
    @Setter
    @Getter
    private long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long venueId;
    private int standingNumberAvailable;
    private int seatingNumberAvailable;
    private int premiumNumberAvailable;

    public ModifyEventDTO() {}

    public ModifyEventDTO(String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, long venueId, int standingNumberAvailable, int seatingNumberAvailable, int premiumNumberAvailable) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venueId = venueId;
        this.standingNumberAvailable = standingNumberAvailable;
        this.seatingNumberAvailable = seatingNumberAvailable;
        this.premiumNumberAvailable = premiumNumberAvailable;
    }
}
