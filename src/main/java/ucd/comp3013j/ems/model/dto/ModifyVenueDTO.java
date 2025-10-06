package ucd.comp3013j.ems.model.dto;

import lombok.Data;

@Data
public class ModifyVenueDTO {
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

    public Long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setManagerPhoneNumber(int managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
}

