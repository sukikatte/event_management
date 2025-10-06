package ucd.comp3013j.ems.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String address;
    private int capacity;
    private String managerName;
    private int managerPhoneNumber;
    private String managerEmail;

    @OneToMany(mappedBy = "venue")
    private List<Event> schedule;

    @ManyToMany
    @JoinTable(
            name = "venue_organizer",
            joinColumns = @JoinColumn(name = "venue_id"),
            inverseJoinColumns = @JoinColumn(name = "organizer_id")
    )
    private List<Organiser> organizers;

    // 构造函数
    public Venue(String name, String address, int capacity, String managerName,
                 int managerPhoneNumber, String managerEmail) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.managerName = managerName;
        this.managerPhoneNumber = managerPhoneNumber;
        this.managerEmail = managerEmail;
    }

}