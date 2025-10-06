package ucd.comp3013j.ems.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModifyVenueDTO {
    @Setter
    @Getter
    private long id;
    private String name;
    private String address;
    private int capacity;
    private String managerName;
    private int managerPhoneNumber;
    private String managerEmail;
    
    public ModifyVenueDTO() {}

    public ModifyVenueDTO(String name, String address, int capacity, String managerName, int managerPhoneNumber, String managerEmail) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.managerName = managerName;
        this.managerPhoneNumber = managerPhoneNumber;
        this.managerEmail = managerEmail;
    }
}

