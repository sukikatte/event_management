package ucd.comp3013j.ems.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.ArrayList;
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

    @GetMapping("/adminVenues")
    public String adminVenues(Authentication a, Model m) {
        if (a.getPrincipal() instanceof AccountWrapper aw) {
            Administrator account = accountService.getAdministratorAccount(aw.getUsername());
            List<Venue> venues = eventService.getAllVenues();

            m.addAttribute("venues", venues);
            m.addAttribute("account", account);
            System.out.println(account.getRole() + " " + account.getEmail());
            if (account.getRole() == ADMINISTRATOR) {
                return "17-list-venues-admin";
            }
        }
        return "22-venues-organiser";
    }

    @GetMapping("/organiserVenues")
    public String organiserVenues(Authentication a, Model m) {
        if (a.getPrincipal() instanceof AccountWrapper aw) {
            Organiser account = accountService.getOrganizerAccount(aw.getUsername());
            List<Venue> venues = eventService.getAllVenues();
            m.addAttribute("venues", venues);
            m.addAttribute("account", account);
            System.out.println(account.getRole() + " " + account.getEmail());
        }
        return "22-venues-organiser";
    }

    @GetMapping("/addVenue")
    public String addVenuePage(Model model) {
        List<Venue> venues = eventService.getAllVenues();
        model.addAttribute("venues", venues);
        return "34-add-venue-organiser";
    }

    @PostMapping("/addVenue")
    public String addVenue(
            Authentication authentication,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam int capacity,
            @RequestParam String managerName,
            @RequestParam int managerPhoneNumber,
            @RequestParam String managerEmail,
            Model m) {


        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Organiser organiser = accountService.getOrganizerAccount(aw.getUsername());

            Venue existingVenue = eventService.getVenue(name);
            if (existingVenue != null) {
                ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event details overlap with an existing one.");
                m.addAttribute("error", "An venue with the same name already exists.");
                m.addAttribute("errorDTO", errdto);
                return "25-modify-venue-error-organiser";
            }

            Venue venue = new Venue(name, address, capacity, managerName, managerPhoneNumber, managerEmail,organiser);
            eventService.addVenue(venue);
            return "redirect:/organiserVenues";
        }
        return null;
    }


    @GetMapping("/displayVenueDetailsAdmin")
    public String displayVenueAdmin(@RequestParam long vId, Model model) {
        try {
            Venue venue = eventService.getVenue(vId);
            if (venue != null) {
                model.addAttribute("venue", venue);
                model.addAttribute("vId", vId);
                return "13-check-venue-admin"; // 确保这是正确的Thymeleaf视图名
            } else {
                model.addAttribute("errorMessage", "Venue not found"); // 可选：向用户显示错误信息
                return "redirect:/adminVenues"; // 如果场馆不存在，则重定向回场馆列表页面
            }
        } catch (Exception e) {
            System.err.println("Error in displayVenueAdmin: " + e.getMessage());
            return "error";
        }
    }
    @GetMapping("/displayVenueDetailsOrganiser")
    public String displayVenueOrganiser(@RequestParam long vId, Model model) {
        try {
            Venue venue = eventService.getVenue(vId);
            if (venue != null) {
                model.addAttribute("venue", venue);
                model.addAttribute("vId", vId);
                return "23-check-venue-organiser"; // 确保这是正确的Thymeleaf视图名
            } else {
                model.addAttribute("errorMessage", "Venue not found"); // 可选：向用户显示错误信息
                return "redirect:/organiserVenues"; // 如果场馆不存在，则重定向回场馆列表页面
            }
        } catch (Exception e) {
            System.err.println("Error in displayVenueOrganiser: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/modifyVenue")
    public String modifyVenue(@RequestParam long vId, Model model) {
        Venue venue = eventService.getVenue(vId);
        if(venue != null) {

        ModifyVenueDTO vdto = new ModifyVenueDTO();
        vdto.setId(venue.getId());
        vdto.setName(venue.getName());
        vdto.setAddress(venue.getAddress());
        vdto.setCapacity(venue.getCapacity());
        vdto.setManagerName(venue.getManagerName());
        vdto.setManagerPhoneNumber(venue.getManagerPhoneNumber());
        vdto.setManagerEmail(venue.getManagerEmail());
        List<Venue> venues = eventService.getAllVenues(); // 所有可用的 Venue



            model.addAttribute("venue", venue);
        model.addAttribute("vDTO", vdto);
        model.addAttribute("venues", venues);
        System.out.println("GO into 24-modify-organiser");
        return "24-modify-venue-organiser";
        }
        model.addAttribute("error", "Venue not found");
        return "redirect:/organiserVenues";
    }

    @PostMapping("/modifyVenue")
    public String modifyVenue(@ModelAttribute ModifyVenueDTO vdto, Model model) {
        Venue venue = eventService.getVenue(vdto.getId());
        System.out.println("VENUE !!! Received vId: " + vdto.getId());
        if (venue == null) {
            model.addAttribute("error", "Venue not found");
            return "redirect:/organiserVenues";
        };

        Venue v = eventService.getVenue(vdto.getName());
        if (v != null && v.getId() != vdto.getId()) {
            ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.VENUE_OVERLAP, "The venue details overlap with an existing one.");
            model.addAttribute("venue", venue);
            model.addAttribute("errorDTO", errdto);
            return "25-modify-venue-error-organiser";
        }

        eventService.modifyVenue(vdto, venue);
        return "redirect:/displayVenueDetailsOrganiser?vId=" + vdto.getId();
    }



    @GetMapping("/deleteVenue")
    public String deleteVenue(@RequestParam long vId) {
        Venue venue = eventService.getVenue(vId);
        if (venue != null) {
            eventService.deleteVenue(venue);
        }
        return "redirect:/adminVenues"; // 或者返回到你想要的页面
    }

    @GetMapping("/confirmDeleteVenue")
    public String confirmDeleteVenue(@RequestParam long vId, Model model) {
        Venue venue = eventService.getVenue(vId);
        if (venue != null) {
            model.addAttribute("venue", venue);
            return "30-delete-venue-warning"; // 返回删除确认页面
        } else {
            model.addAttribute("errorMessage", "Venue not found");
            return "error"; // 如果场地不存在，显示错误页面
        }
    }
    @GetMapping("/performDelete")
     public String performDelete(@RequestParam long vId, Model model) {
         // 处理逻辑
         return "redirect:/deleteVenue?vId=" + vId;
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
            model.addAttribute("ticketHistory", (eventService.getTicketsHistory(u)));
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
            return "10-buy-tickets";
        } else {
            model.addAttribute("error", "Insufficient tickets available.");
            return "32-buy-ticket-error";
        }
    }

    /**
     * Buy a ticket for a specific event with an account ID.
     * This will be called after user clicks "buy" button.
     *
     * @param eId        The event ID.
     * @param ticketType The type of ticket.
     * @param ticketNum  The number of tickets.
     * @param model      The model to populate with ticket purchase result.
     * @return The view for the ticket info confirmation.
     */
    @PostMapping("/check_tickets")
    public String checkTicket(Authentication authentication,
                            @RequestParam(name="eId") long eId,
                            @RequestParam(name = "ticket-type") String ticketType,
                            @RequestParam(name = "ticket-number") int ticketNum,
                            Model model) {
        List<Ticket> tickets = null;
        System.out.println("buyTicket" + ticketNum);
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            User u = accountService.getUserAccount(aw.getUsername());
            System.out.println("account: " + aw.getUsername() + aw.getClass());

            tickets = eventService.buyTicket(u.getId(), eId, mapToTicketType(ticketType), ticketNum);
            u.addTicket(tickets);
            model.addAttribute("tickets", tickets);
        }
        return tickets != null ? "11-check-tickets" : "32-buy-ticket-error";
    }

    private TicketType mapToTicketType(String ticketType) {
        switch (ticketType) {
            case "1": return TicketType.STANDING;
            case "2": return TicketType.SEATING;
            case "3": return TicketType.PREMIUM;
            default: throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
        }
    }


    /**
     * Display a specific ticket's details.
     * This will be called when user click a ticket to view its details.
     *
     * @param tId   The ID of the ticket.
     * @param model The model to populate with ticket details.
     * @return The view name for displaying the ticket details.
     */
    @GetMapping("/ticket_detail")
    public String displayTicket(@RequestParam long tId, Model model) {
        model.addAttribute("ticket", eventService.getTicket(tId));
        return "35-ticket_detail";
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
        events.forEach(event -> System.out.println("here!!!"+event.getName()));
        m.addAttribute("events", events);
        m.addAttribute("account", account);
        return "03-main-user";
    }

    @GetMapping("/addEvent")
    public String addEventPage(Model model) {
        List<Venue> venues = eventService.getAllVenues();
        model.addAttribute("venues", venues);
        return "33-add-event-organiser";
    }

    @PostMapping("/addEvent")
    public String addEvent(
            Authentication authentication,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            @RequestParam long venueId,
            Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Organiser organiser = accountService.getOrganizerAccount(aw.getUsername());
            Venue venue = eventService.getVenue(venueId);

            Event existingEvent = eventService.getEvent(name);
            if (existingEvent != null) {
                ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event details overlap with an existing one.");
                model.addAttribute("error", "An event with the same name already exists.");
                model.addAttribute("errorDTO", errdto);
                return "29-modify-event-error";
            }

            int capacity = venue.getCapacity();
            int standingNumberAvailable = capacity / 3;
            int seatingNumberAvailable = capacity / 3;
            int premiumNumberAvailable = capacity - standingNumberAvailable - seatingNumberAvailable;

            Event event = new Event(name, description, date, startTime, endTime, standingNumberAvailable, seatingNumberAvailable, premiumNumberAvailable, venue, organiser);
            eventService.addEvent(event);

            return "redirect:/getAllEvents"; // 重定向到事件列表页面
        }
        return "error";
    }

//    @PostMapping("/addEvent")
//    public String addEvent(
//            Authentication authentication,
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam LocalDate date,
//            @RequestParam LocalTime startTime,
//            @RequestParam LocalTime endTime,
//            @RequestParam int standingNumberAvailable,
//            @RequestParam int seatingNumberAvailable,
//            @RequestParam int premiumNumberAvailable,
//            @RequestParam long venue_id,
//            Model m) {
//        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
//            Organiser account = accountService.getOrganizerAccount(aw.getUsername());
//
//
//            if (eventRepository.findByName(name) != null) {
//                ErrorMessageDTO errorDTO = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event overlaps with an existing one.");
//                m.addAttribute("errorDTO", errorDTO);
//                return "29-modify-event-error";
//            }
//
//            eventService.addEvent(name, description, date, startTime, endTime,
//                    standingNumberAvailable, seatingNumberAvailable, premiumNumberAvailable,account, venue_id);
//            return "33-add-event-organiser";
//        }
//        return null;
//    }


    /**
     * Retrieves all organiser's events and displays them in a view.
     *
     * @return The name of the view to render, which displays the list of events.
     */

//    @GetMapping("/getAllEvents")
//    public String getOrganiserAllEvents(Model model) {
//        List<Event> events = eventService.getAllEvents(); // Fetch all events
//        model.addAttribute("events", events); // Add events to the model
//        return "20-events-organiser"; // Return the events-organiser page
//    }
    /*@GetMapping("/getAllEvents")
    public String getOrganiserAllEvents(Model model, Organiser organiser) {
        // 获取当前认证的用户（假设是 Organiser 类型）
        //Organiser organiser = (Organiser) userDetails;
        // 获取当前组织者的所有事件
        List<Event> events = eventService.getEventsByOrganiser(organiser);
        // 将事件添加到模型中
        model.addAttribute("events", events);
        // 返回显示组织者事件的视图
        return "20-events-organiser"; // 返回 events-organiser 页面
    }*/

    @GetMapping("/getAllEvents")
    public String getOrganiserAllEvents(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            // 获取当前登录的组织者
            Organiser organiser = accountService.getOrganizerAccount(aw.getUsername());

            // 获取当前组织者创建的所有事件
            List<Event> events = eventService.getEventsByOrganiser(organiser);

            // 将事件添加到模型中
            model.addAttribute("events", events);
        } else {
            // 如果无法获取当前登录用户，返回一个空的事件列表
            model.addAttribute("events", new ArrayList<>());
        }

        // 返回视图
        return "20-events-organiser";
    }


    @GetMapping("/displayEventDetailsOrganiser")
    public String displayEventDetailsOrganiser(@RequestParam long eId, Model model) {
        System.out.println("Received eId: " + eId);
        try {
            Event event = eventService.getEvent(eId);
            if (event != null) {
                model.addAttribute("event", event);
                model.addAttribute("eId", eId);
                return "18-check-event-organiser"; // 确保这是正确的Thymeleaf视图名
            } else {
                model.addAttribute("errorMessage", "Event not found"); // 可选：向用户显示错误信息
                return "redirect:/getAllEvents"; // 如果场馆不存在，则重定向回场馆列表页面
            }
        } catch (Exception e) {
            System.err.println("Error in displayVenueAdmin: " + e.getMessage());
            return "error";
        }
    }


    /**
     * Displays the modification form for a specific event.
     *
     * @param eId   The ID of the event to modify.
     * @param model The Model object used to pass event data and DTO to the view.
     * @return The name of the view to render, which displays the event modification form.
     */
    @GetMapping("/modifyEvent")
    public String modifyEvent(@RequestParam long eId, Model model) {
        Event event = eventService.getEvent(eId);

        if (event != null) {
            // 创建 ModifyEventDTO 并填充当前事件的数据
            ModifyEventDTO edto = new ModifyEventDTO();
            edto.setId(event.getId()); // 添加事件 ID
            edto.setName(event.getName());
            edto.setDescription(event.getDescription());
            edto.setDate(event.getDate());
            edto.setStartTime(event.getStartTime());
            edto.setEndTime(event.getEndTime());


            // 获取当前事件的 Venue 和所有可用的 Venue
            Venue currentVenue = event.getVenue(); // 当前事件的 Venue
            List<Venue> venues = eventService.getAllVenues(); // 所有可用的 Venue

            if (currentVenue != null) {
                model.addAttribute("currentVenue", currentVenue); // 当前事件的 Venue
            }

            // 将数据添加到模型中供 Thymeleaf 使用
            model.addAttribute("event", event); // 当前事件
            model.addAttribute("eDTO", edto); // 添加 edto 给模板
            model.addAttribute("currentVenue", currentVenue); // 当前事件的 Venue
            model.addAttribute("venues", venues); // 所有可用的 Venue

            return "19-modify-event-organiser";
        }

        model.addAttribute("error", "Event not found");
        return "redirect:/getAllEvents";
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
        Event event = eventService.getEvent(edto.getId()); // 获取事件 ID

        if (event == null) {
            model.addAttribute("error", "Event not found");
            return "redirect:/getAllEvents";
        }
        //System.out.println("Received edto venue id: " + edto.getVenueId());

        // 检查事件是否与已有事件重叠
        Event e = eventService.getEvent(edto.getName());
        if (e != null && e.getId() != edto.getId()) {
            ErrorMessageDTO errdto = new ErrorMessageDTO(ErrorType.EVENT_OVERLAP, "The event details overlap with an existing one.");
            model.addAttribute("event", event);
            model.addAttribute("errorDTO", errdto);
            return "29-modify-event-error";
        }
        //System.out.println("Received edto venue id: " + edto.getVenueId());

        eventService.modifyEvent(edto, event);
        return "redirect:/displayEventDetailsOrganiser?eId=" + edto.getId(); // 跳转到事件确认页面
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
        try{
        Event event = eventService.getEvent(eId);
        if (event == null) {
            System.out.println("Event not found for eId: " + eId);
        }
        m.addAttribute("event", event);
        m.addAttribute("eId", eId);
        return "34-check-other-event-organiser";
        } catch (Exception e) {
            System.err.println("Error in displayEventDetailOrganiser: " + e.getMessage());
            return "error"; // Redirect to a proper error page
        }
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
            return null;
        }
    }



    @GetMapping("/displayEventDetailAdmin")
    public String displayEventDetailAdmin(@RequestParam long eId, Model m, Authentication authentication) {
        try {
            System.out.println("Attempting to fetch event details for Event ID: " + eId);
            // 打印当前认证用户的详情
            if (authentication != null) {
                System.out.println("Current user: " + authentication.getName());
                System.out.println("Authorities: " + authentication.getAuthorities());
            }

            Event event = eventService.getEvent(eId);
            if (event == null) {
                System.out.println("No event found for ID: " + eId);
                m.addAttribute("errorMessage", "Event not found for ID: " + eId);
                return "error";
            }

            System.out.println("Admin requested details for Event ID: " + eId);
            System.out.println("Event Name: " + event.getName()); // 打印事件名称，确认是否正确获取
            m.addAttribute("event", event);
            m.addAttribute("eId", eId);
            return "14-check-event-admin";
        } catch (Exception e) {
            System.err.println("Error in displayEventDetailAdmin: " + e.getMessage());
            m.addAttribute("error", "Unable to display event details: " + e.getMessage());
            return "error"; // 同上，确保错误页面可以显示信息
        }
    }

    // delete event
    @GetMapping("/deleteEvent")
    public String deleteEvent(@RequestParam long eId) {
        Event event = eventService.getEvent(eId);
        if (event != null) {
            eventService.deleteEvent(event);
        }
        return "redirect:/administrator"; // 或者返回到你想要的页面
    }

    @GetMapping("/confirmDeleteEvent")
    public String confirmDeleteEvent(@RequestParam long eId, Model model) {
        Event event = eventService.getEvent(eId);
        if (event != null) {
            model.addAttribute("event", event);
            return "31-delete-event-warning"; // 返回删除确认页面
        } else {
            model.addAttribute("errorMessage", "Event not found");
            return "error"; // 如果场地不存在，显示错误页面
        }
    }
    @GetMapping("/performDeleteEvent")
    public String performDeleteEvent(@RequestParam long eId, Model model) {
        // 处理逻辑
        return "redirect:/deleteEvent?eId=" + eId;
    }

}


