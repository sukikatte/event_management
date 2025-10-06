package ucd.comp3013j.ems.model.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ModifyEventDTO {
    private long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int standingNumberAvailable;
    private int seatingNumberAvailable;
    private int premiumNumberAvailable;

    public ModifyEventDTO() {}

    public ModifyEventDTO(String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, int standingNumberAvailable, int seatingNumberAvailable, int premiumNumberAvailable) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.standingNumberAvailable = standingNumberAvailable;
        this.seatingNumberAvailable = seatingNumberAvailable;
        this.premiumNumberAvailable = premiumNumberAvailable;
    }
    public Long getId(){
        return id;
    }

}
