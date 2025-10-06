package ucd.comp3013j.ems.model.dto;

import lombok.Data;

@Data
public class ModifyAccountDTO  {
    public String name;
    public String email;
    public String password;
    public String role;
    public String companyName;
    public String address;
    public String phoneNumber;
    private long id;

    public ModifyAccountDTO() {
    }

    public ModifyAccountDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ModifyAccountDTO(String name, String email, String password, String companyName, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId(){
        return id;
    }
}
