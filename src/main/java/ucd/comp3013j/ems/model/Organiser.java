package ucd.comp3013j.ems.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity @Data
public class Organiser extends Account {
    private String companyName;
    private String address;
    private String phoneNumber;

    public Organiser(String name, String email, String password, String companyName, String address, String phoneNumber) {
        super(email, name, password);
        this.setRole(Role.ORGANISER);
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Organiser() {
        super("", "", "");
        this.setRole(Role.ORGANISER);
    }
}
