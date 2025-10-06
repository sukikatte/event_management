package ucd.comp3013j.ems.model.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ucd.comp3013j.ems.model.Ticket;
import ucd.comp3013j.ems.model.User;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByAdmission(String admission) ;

    List<Ticket> findAllByUser(User user);

    Ticket findById(long id);
}
