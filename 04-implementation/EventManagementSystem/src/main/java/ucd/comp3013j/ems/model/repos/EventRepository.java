package ucd.comp3013j.ems.model.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ucd.comp3013j.ems.model.Account;
import ucd.comp3013j.ems.model.Event;
import ucd.comp3013j.ems.model.Organiser;
import ucd.comp3013j.ems.model.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);

    Event findById(long i);

    List<Event> findAllByOrganiser(Organiser organiser);

    List<Event> findAll();

    List<Event> findByVenueId(Long venueId);
}
