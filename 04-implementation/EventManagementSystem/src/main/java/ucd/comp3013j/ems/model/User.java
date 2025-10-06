package ucd.comp3013j.ems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ucd.comp3013j.ems.model.dto.ModifyAccountDTO;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "\"user\"")
@Data
public class User extends Account {
    @OneToMany
    List<Ticket> tickets;
    public User(ModifyAccountDTO registrationDTO) {
        this(registrationDTO.getEmail(), registrationDTO.getName(), registrationDTO.getPassword());
        this.setRole(Role.USER);
    }

    public User() {
        super();
        this.setRole(Role.USER);
        tickets = new ArrayList<>();
    }

    public User(String name, String email, String password){
        super(email,name, password);
        this.setRole(Role.USER);
        tickets = new ArrayList<>();
    }

    public List<Ticket> getTicketsHistory() {
        return tickets;
    }

    public void addTicket(List<Ticket> new_tickets){
        tickets.addAll(new_tickets);
    }
}
