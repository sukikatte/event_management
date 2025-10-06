package ucd.comp3013j.ems.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ucd.comp3013j.ems.model.*;
import ucd.comp3013j.ems.model.dto.ErrorMessageDTO;
import ucd.comp3013j.ems.model.dto.ModifyEventDTO;
import ucd.comp3013j.ems.model.dto.ModifyVenueDTO;
import ucd.comp3013j.ems.model.repos.EventRepository;
import ucd.comp3013j.ems.model.repos.VenueRepository;
import ucd.comp3013j.ems.model.services.AccountService;
import ucd.comp3013j.ems.model.services.EventService;
import ucd.comp3013j.ems.websecurity.AccountWrapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ucd.comp3013j.ems.model.Role.ADMINISTRATOR;


@Controller
public class EventController {

    private final AccountService accountService;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private VenueRepository venueRepository;

    @Autowired
    public EventController(AccountService as, EventService es, EventRepository er) {
        this.accountService = as;
        this.eventService = es;
        this.eventRepository = er;
        System.out.println("accountService = " + accountService);
        System.out.println("eventService = " + eventService);
        System.out.println("eventRepository = " + eventRepository);
    }


    ////////////////////////////////venue-related/////////////////////////////////

    @GetMapping("/displayVenuesById")
    public String displayVenues(@RequestParam long accountId, Model m) {
        Account account = accountService.getAccount(accountId);

        List<Venue> venues = eventService.getAllVenues();
        m.addAttribute("venues", venues);
        m.addAttribute("account", account);

        if (account.getRole() == ADMINISTRATOR) {
            return "17-list-venues-admin";
        }
        return "22-venues-organiser";
    }


    @PostMapping("/addVenue")
    public String addVenue(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam int capacity,
            @RequestParam String managerName,
            @RequestParam int managerPhoneNumber,
            @RequestParam String managerEmail,
            Model m) {

        Venue venue = new Venue(name, address, capacity, managerName, managerPhoneNumber, managerEmail);

        Venue isOverlapping = venueRepository.findByName(venue.getName());
        if (isOverlapping != null) {
            ErrorMessageDTO errorDTO = new ErrorMessageDTO(ErrorType.VENUE_OVERLAP, "The venue overlaps with an existing one.");
            m.addAttribute("errorDTO", errorDTO);
            return "26-add-venues-error-organiser";
        }

        eventService.addVenue(name, address, capacity, managerName, managerPhoneNumber, managerEmail);
        return "22-venues-organiser";
    }


    @GetMapping("/displayVenueDetails")
    public String displayVenue(@RequestParam long vId, Model m) {
        Venue venue = eventService.getVenue(vId);
        m.addAttribute("venue", venue);

        return "venue-details";

    }


    @GetMapping("/modifyVenue")
    public String modifyVenue(@RequestParam long vId, Model model) {
        Venue venue = eventService.getVenue(vId);

        ModifyVenueDTO vdto = new ModifyVenueDTO();
        vdto.setId(venue.getId());
        vdto.setName(venue.getName());
        vdto.setAddress(venue.getAddress());
        vdto.setCapacity(venue.getCapacity());
        vdto.setManagerName(venue.getManagerName());
        vdto.setManagerPhoneNumber(venue.getManagerPhoneNumber());
        vdto.setManagerEmail(venue.getManagerEmail());

        model.addAttribute("venue", venue);
        model.addAttribute("vDTO", vdto);

        return "modify-venue";
    }

    @PostMapping("/modifyVenue")
    public String modifyVenue(@ModelAttribute ModifyVenueDTO vdto, Model model) {
        Venue isOverlapping = venueRepository.findByName(vdto.getName());
        Venue venue = eventService.getVenue(vdto.getId());

        if (isOverlapping != null) {
            ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.VENUE_OVERLAP, "The venue name already exists.");
            model.addAttribute("venue", venue);
            model.addAttribute("errorDTO", errdto);

            return "modify-venue-error";
        }

        eventService.modifyVenue(vdto, venue);
        return "modify-venue";
    }


    @PostMapping("/deleteVenue")
    public String deleteVenue(@RequestParam long vId, Model m) {
        Venue venue = eventService.getVenue(vId);
        if (venue != null) {
            eventService.deleteVenue(venue);
        }
        eventService.getAllVenues();
        return "delete-venue";
    }


    ////////////////////////////////ticket-related/////////////////////////////////

    /**
     * View ticket purchase history for a specific account.
     *
     * @return The view for displaying ticket history.
     */
    @GetMapping("/tickets")
    public String viewTicketsHistory(Authentication authentication, Model model) {
        System.out.println("viewTicketsHistory");
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            User u = accountService.getUserAccount(aw.getUsername());
            System.out.println("account: " + aw.getUsername());
            model.addAttribute("ticketHistory", (u.getTicketsHistory()));
        }
        return "09-tickets-user";
    }


    /**
     * Check the available ticket number for a specific event before buying (logged-in user context).
     * This should be called everytime the user clicks "+" in ticket number section.
     *
     * @param eId        The event ID.
     * @param ticketType The type of ticket.
     * @param ticketNum  The number of tickets.
     * @param model      The model to populate with ticket purchase result.
     * @return If there exists sufficient ticket number, return the refreshed buy ticket view;
     * if not, return the error view.
     */
    @PostMapping("/buy_tickets")
    public String buyTicket(@RequestParam long eId,
                            @RequestParam TicketType ticketType,
                            @RequestParam int ticketNum,
                            Model model) {
        ErrorType errorType = eventService.getAvailableTicketNumber(eId, ticketType, ticketNum);
        if (errorType.equals(ErrorType.NONE)) {
            Event event = eventService.getEvent(eId);
            model.addAttribute("event", event);
            return "10-buy-tickets";
        }
        return "32-buy-ticket-error";
    }

    /**
     * Buy a ticket for a specific event with an account ID.
     * This will be called after user clicks "buy" button.
     *
     * @param accountId  The ID of the account making the purchase.
     * @param eId        The event ID.
     * @param ticketType The type of ticket.
     * @param ticketNum  The number of tickets.
     * @param model      The model to populate with ticket purchase result.
     * @return The view for the ticket info confirmation.
     */
    @PostMapping("/check_tickets")
    public String buyTicket(@RequestParam long accountId,
                            @RequestParam long eId,
                            @RequestParam TicketType ticketType,
                            @RequestParam int ticketNum,
                            Model model) {
        List<Ticket> tickets = eventService.buyTicket(accountId, eId, ticketType, ticketNum);
        User u = (User) accountService.getAccount(accountId);
        model.addAttribute("tickets", u.getTicketsHistory());
        return tickets != null ? "11-check-tickets" : "32-buy-ticket-error";
    }

    /**
     * Display a specific ticket's details.
     * This will be called when user click a ticket to view its details.
     *
     * @param tId   The ID of the ticket.
     * @param model The model to populate with ticket details.
     * @return The view name for displaying the ticket details.
     */
    @GetMapping("/tickets/detail")
    public String displayTicket(@RequestParam long tId, Model model) {
        model.addAttribute("ticket", eventService.getTicket(tId));
        return "11-check-tickets";
    }


    ////////////////////////////////event-related/////////////////////////////////

    /**
     * Displays the details of all events associated with a specific account.
     *
     * @param accountId The ID of the account whose associated events will be displayed.
     * @param m         The Model object used to populate the view with account and event details.
     * @return The name of the view template for displaying the list of events.
     */
    @GetMapping("/displayEvent")
    public String displayEvent(@RequestParam String accountId, Model m) {
        Account account = accountService.getAccount(accountId);
        List<Event> events = eventService.getAllEvents();
        m.addAttribute("events", events);
        m.addAttribute("account", account);
        return "list-events";
    }


    @GetMapping("/addEvent")
    public String addEvent(Model model) {
        model.addAttribute("venues", eventService.getAllVenues());
        return "19-modify-event-organiser";
    }

    @PostMapping("/addEvent")
    public String addEvent(
            Authentication authentication,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            @RequestParam int standingNumberAvailable,
            @RequestParam int seatingNumberAvailable,
            @RequestParam int premiumNumberAvailable,
            @RequestParam long venue_id,
            Model m) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Organiser account = accountService.getOrganizerAccount(aw.getUsername());


            if (eventRepository.findByName(name) != null) {
                ErrorMessageDTO errorDTO = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event overlaps with an existing one.");
                m.addAttribute("errorDTO", errorDTO);
                return "29-modify-event-error";
            }

            eventService.addEvent(name, description, date, startTime, endTime,
                    standingNumberAvailable, seatingNumberAvailable, premiumNumberAvailable,account, venue_id);
            return "19-modify-event-organiser";
        }
        return null;
    }


    /**
     * Retrieves all organiser's events and displays them in a view.
     *
     * @param m The Model object used to pass data to the view.
     * @return The name of the view to render, which displays the list of events.
     */

    @GetMapping("/getAllEvents")
    public String getOrganiserAllEvents(Model m) {
        // 直接获取事件列表并显示
        List<Event> events = eventService.getAllEvents();
        m.addAttribute("events", events);
        return "20-events-organiser";  // 返回事件页面
    }


    /**
     * Displays the modification form for a specific event.
     *
     * @param eId   The ID of the event to modify.
     * @param model The Model object used to pass event data and DTO to the view.
     * @return The name of the view to render, which displays the event modification form.
     */
    @GetMapping("/modifyEvent")
    public void modifyEvent(@RequestParam long eId, Model model) {
        Event event = eventService.getEvent(eId);

        ModifyEventDTO edto = new ModifyEventDTO();
        edto.setName(event.getName());
        edto.setDescription(event.getDescription());
        edto.setStartTime(event.getStartTime());
        edto.setEndTime(event.getEndTime());
        edto.setStandingNumberAvailable(event.getStandingNumberAvailable());
        edto.setSeatingNumberAvailable(event.getSeatingNumberAvailable());
        edto.setPremiumNumberAvailable(event.getPremiumNumberAvailable());

        model.addAttribute("event", event);
        model.addAttribute("eDTO", edto);
    }


    /**
     * Handles the submission of the event modification form.
     *
     * @param edto  The ModifyEventDTO object containing updated event details.
     * @param model The Model object used to pass error messages or other data to the view.
     * @return A redirect to the event list page on successful modification,
     * or the modification form view with error details if validation fails.
     */
    @PostMapping("/modifyEvent")
    public String modifyEvent(@ModelAttribute ModifyEventDTO edto, Model model) {
        Event event = new Event();
        if (eventService.getEvent(edto.getName()) != null) {
            ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event details overlap with an existing one.");
            event = eventService.getEvent(edto.getId());
            model.addAttribute("event", event);
            model.addAttribute("errorDTO", errdto);
            return "29-modify-event-error";
        }

        eventService.modifyEvent(edto, event);
        return "18-check-event-organiser";
    }


    /**
     * Handles the deletion of a specific event.
     *
     * @param eId The ID of the event to be deleted.
     * @param m   The Model object used to pass data to the view, if necessary.
     * @return A redirect to the event list page with either a success or error status.
     */
    @PostMapping("/deleteEvent")
    public String deleteEvent(@RequestParam long eId, Model m) {
        Event event = eventService.getEvent(eId);
        if (event != null) {
            eventService.deleteEvent(event);
        }
        eventService.getAllEvents();
        return "31-delete-event-warning";
    }


    /**
     * Displays the details of a specific event for user.
     *
     * @param eId The ID of the event whose details are to be displayed.
     * @param m   The Model object used to pass the event data to the view.
     * @return The name of the view that displays the event details.
     */
    @GetMapping("/displayEventDetailOrganiser")
    public String displayEventDetailOrganiser(@RequestParam long eId, Model m) {
        System.out.println("eId: " + eId);
        Event event = eventService.getEvent(eId);
        System.out.println(eId);
        if (event == null) {
            System.out.println("Event not found for eId: " + eId);
        }
        m.addAttribute("event", event);
        return "11-check-tickets";
    }

    @GetMapping("/displayEventDetailUser")
    public String displayEventDetailUser(@RequestParam long eId, Model m) {
        try {
            Event event = eventService.getEvent(eId);
            if (event == null) {
                throw new IllegalArgumentException("Event not found for ID: " + eId);
            }
            System.out.println("Request received for Event ID: " + eId);
            m.addAttribute("event", event);
            m.addAttribute("eId", eId);
            return "10-buy-tickets";
        } catch (Exception e) {
            System.err.println("Error in displayEventDetailUser: " + e.getMessage());
            return "error"; // Redirect to a proper error page
        }
    }



    @GetMapping("/displayEventDetailAdmin")
    public String displayEventDetailAdmin(@RequestParam long eId, Model m) {
        Event event = eventService.getEvent(eId);
        m.addAttribute("event", event);
        return "14-check-event-admin";
    }
}

