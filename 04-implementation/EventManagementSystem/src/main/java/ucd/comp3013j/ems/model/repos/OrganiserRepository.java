package ucd.comp3013j.ems.model.repos;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import ucd.comp3013j.ems.model.Organiser;
import ucd.comp3013j.ems.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
    Organiser findByEmail(String email) ;

    Organiser findById(long i);
}
