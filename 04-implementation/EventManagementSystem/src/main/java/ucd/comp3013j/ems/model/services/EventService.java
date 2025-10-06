package ucd.comp3013j.ems.model.services;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ucd.comp3013j.ems.model.*;
import ucd.comp3013j.ems.model.dto.ModifyEventDTO;
import ucd.comp3013j.ems.model.dto.ModifyVenueDTO;
import ucd.comp3013j.ems.model.repos.*;
import org.springframework.security.core.GrantedAuthority;
import ucd.comp3013j.ems.websecurity.AccountWrapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ucd.comp3013j.ems.model.Role.ADMINISTRATOR;
import static ucd.comp3013j.ems.model.Role.ORGANISER;

@Service
public class EventService {
    private AdminRepository adminRepository;
    private UserRepository userRepository;
    private OrganiserRepository organiserRepository;
    private EventRepository eventRepository;
    private VenueRepository venueRepository;
    private TicketRepository ticketRepository;


    @Autowired
    public EventService(EventRepository er, VenueRepository vr, TicketRepository tr, OrganiserRepository or, AdminRepository ad, UserRepository user) {
        this.eventRepository = er;
        this.venueRepository = vr;
        this.ticketRepository = tr;
        this.organiserRepository = or;
        this.adminRepository = ad;
        this.userRepository = user;
    }



    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("AccountDetailsService.mapRolesToAuthorities: " + role.toString());
        System.out.println("AccountDetailsService.mapRolesToAuthorities: " + new SimpleGrantedAuthority(role.toString()));
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    // Venue related

    public Venue getVenue(String name){
        Venue v = venueRepository.findByName(name);
        return v;
    }

    public Venue getVenue(long vId) {
        Venue v = venueRepository.findById(vId).orElse(null);
        return v;
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public ErrorType addVenue(String name, String address, int capacity, String contactName, int contactNumber, String contactEmail,Organiser o) {
        Venue v = venueRepository.findByName(name);
        if (v != null){
            return ErrorType.VENUE_OVERLAP;
        }
        Venue venue = new Venue(name, address, capacity, contactName, contactNumber, contactEmail,o);
        venueRepository.save(venue);
        return ErrorType.NONE;
    }
    public void addVenue(Venue venue) {
        venueRepository.save(venue);
    }

    public ErrorType modifyVenue(ModifyVenueDTO vdto, Venue venue){
        if (getVenue(vdto.getName()) != null){
            return ErrorType.VENUE_OVERLAP;
        }
        venue.setName(vdto.getName());
        venue.setAddress(vdto.getAddress());
        venue.setCapacity(vdto.getCapacity());
        venue.setManagerName(vdto.getManagerName());
        venue.setManagerPhoneNumber(vdto.getManagerPhoneNumber());
        venue.setManagerEmail(vdto.getManagerEmail());
        venueRepository.save(venue);
        return ErrorType.NONE;
    }

    @Transactional
    public void deleteVenue(Venue v) {
        // 更新相关事件的 venue 字段为 null
        updateEventsWithNullVenue(v.getId());
        venueRepository.delete(v);
    }

    @Transactional
    public void updateEventsWithNullVenue(Long venueId) {
        List<Event> events = eventRepository.findByVenueId(venueId);
        for (Event event : events) {
            event.setVenue(null);
            eventRepository.save(event);
        }
    }


    // Event related
    public ErrorType addEvent(String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime,
                              int standingNumberAvailable, int seatingNumberAvailable, int premiumNumberAvailable, Organiser o, long venueId){
        Event e = eventRepository.findByName(name);
        if (e != null){
            return ErrorType.EVENT_OVERLAP;
        }
        Venue venue = venueRepository.findById(venueId).orElse(null);
        Event event = new Event(name, description, date, startTime, endTime, standingNumberAvailable, seatingNumberAvailable, premiumNumberAvailable, venue, o);
        eventRepository.save(event);
        return ErrorType.NONE;
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public Event getEvent(String name){
        return eventRepository.findByName(name);
    }

    public Event getEvent(long eId){
        return eventRepository.findById(eId);
    }

    public void deleteEvent(Event event){
        eventRepository.delete(event);
    }

    public ErrorType modifyEvent(ModifyEventDTO edto, Event event){
        if (getEvent(edto.getName()) != null && getEvent(edto.getName()).getId() != edto.getId()){
            return ErrorType.EVENT_OVERLAP;
        }
        Venue newVenue = getVenue(edto.getVenueId());

        event.setName(edto.getName());
        event.setDescription(edto.getDescription());
        event.setDate(edto.getDate());
        event.setStartTime(edto.getStartTime());
        event.setEndTime(edto.getEndTime());
        event.setVenue(newVenue);

        eventRepository.save(event);
        return ErrorType.NONE;
    }

    public List<Event> getAllEvents(){
        System.out.println("getAllEvents()");
        return eventRepository.findAll();
    }

    public List<Event> getEventHistory(Account account){
        if (account.getRole() == ADMINISTRATOR){
            return eventRepository.findAll();
        } else if (account.getRole() == ORGANISER ) {
            return eventRepository.findAllByOrganiser((Organiser) account);
        }
        return null;
    }

    // Ticket related
    public List<Ticket> getTicketsHistory(User u){
        return ticketRepository.findAllByUser(u);
    }

    public ErrorType getAvailableTicketNumber(long eid, TicketType ticketType, int ticketNum) {
        if (ticketType == TicketType.STANDING) {
            if (eventRepository.findById(eid).getStandingNumberAvailable() >= ticketNum) {
                return ErrorType.NONE;
            }
        } else if (ticketType == TicketType.SEATING) {
            if (eventRepository.findById(eid).getSeatingNumberAvailable() >= ticketNum) {
                return ErrorType.NONE;
            }
        } else if (ticketType ==TicketType.PREMIUM) {
            if (eventRepository.findById(eid).getPremiumNumberAvailable() >= ticketNum) {
                return ErrorType.NONE;
            }
        }
        return ErrorType.NO_TICKET;
    }

    public  Ticket getTicket(long tid){
        return ticketRepository.findById(tid);
    }

    public String getOrganiserName(Organiser o){
        return o.getName();
    }

    public List<Ticket> buyTicket(long accountId, long eId, TicketType ticketType, int ticketNum) {
        Event e = getEvent(eId);
        User u = userRepository.findById(accountId);
        List<Ticket> newTickets = new ArrayList<>();
        for (int i = 0; i < ticketNum; i++) {
            Ticket t = new Ticket(u, e, ticketType);
            if (ticketType.equals(TicketType.STANDING)) {
                modifyEvent(new ModifyEventDTO(e.getName(), e.getDescription(), e.getDate(), e.getStartTime(), e.getEndTime(), e.getVenue().getId(), e.getStandingNumberAvailable() - ticketNum, e.getSeatingNumberAvailable(), e.getPremiumNumberAvailable()), e);
            } else if (ticketType.equals(TicketType.SEATING)) {
                modifyEvent(new ModifyEventDTO(e.getName(), e.getDescription(), e.getDate(), e.getStartTime(), e.getEndTime(), e.getVenue().getId(), e.getStandingNumberAvailable(), e.getSeatingNumberAvailable() - ticketNum, e.getPremiumNumberAvailable()), e);
            } else if (ticketType.equals(TicketType.PREMIUM)) {
                modifyEvent(new ModifyEventDTO(e.getName(), e.getDescription(), e.getDate(), e.getStartTime(), e.getEndTime(), e.getVenue().getId(), e.getStandingNumberAvailable(), e.getSeatingNumberAvailable(), e.getPremiumNumberAvailable() - ticketNum), e);
            }
            System.out.println(t.getAdmission());
            ticketRepository.save(t);
            newTickets.add(t);
        }
        return newTickets;
    }

    public List<Event> getEventsByOrganiser(Organiser organiser) {
        return eventRepository.findAllByOrganiser(organiser);
    }

}
