### Message From Sean

I have now checked out the contents of the repository for grading. Any further details added after the deadline will be ignored.


# Team Project: *Group 01*

## Team Members
| Number | Name          | Email(s)                  | CSGitLab Username |
|--------|---------------|---------------------------|-------------------|
| TM1    | Qiyue Zhu     | qiyue.zhu@ucdconnect.ie   | @Victoria         |
| TM2    | Wenyi Liang   | wenyi.liang@ucdconnect.ie | @Wenyi            |     
| TM3    | Yunfei Xu     | yunfei.xu@ucdconnect.ie   | @Kira210          |     
| TM4    | Zitong Wang   | zitong.wang@ucdconnect.ie | @sukikatte        |     
| TM5    | Siqi Du       | siqi.du@ucdconnect.ie     | @Yuki             |     
| TM6    | Jiayi Cai     | jiayi.cai@ucdconnect.ie   | @22207285         |     
| TM7    | Yani Yang     | yani.yang@ucdconnect.ie   | @hs               |     
| TM8    | Boran Duan    | boran.duan@ucdconnect.ie  | @boran            |

# Analysis

This phase continues the development process by realising the courses of events that we described in the requirements analysis phase. This requires the creation of sequence diagrams for each course of events. Decisions made while constructing these diagrams are also documented in the class diagram.

## Use Case Realisations (Sequence Diagrams)

Each use case contains the separate sequence diagrams for each course of events. The diagrams are stored in the `images` folder and referenced in the markdown files.

1. [Creat Account](/02-analysis/usecases/docs/01-use-case-CreatAccount.md)
2. [List Account](/02-analysis/usecases/docs/02-use-case-ListAccount.md)
3. [View Account](/02-analysis/usecases/docs/03-use-case-ViewAccount.md)
4. [Modify Account](/02-analysis/usecases/docs/04-use-case-ModifyAccount.md)
5. [Register Account](/02-analysis/usecases/docs/05-use-case-RegisterAccount.md)
6. [List Venues](/02-analysis/usecases/docs/06-use-case-ListVenues.md)
7. [Add Venue](/02-analysis/usecases/docs/07-use-case-AddVenue.md)
8. [View Venue](/02-analysis/usecases/docs/08-use-case-ViewVenue.md)
9. [Modify Venue](/02-analysis/usecases/docs/09-use-case-ModifyVenue.md)
10. [Delete Venue](/02-analysis/usecases/docs/10-use-case-DeleteVenue.md)
11. [List Events](/02-analysis/usecases/docs/11-use-case-ListEvents.md)
12. [Add Event](/02-analysis/usecases/docs/12-use-case-AddEvent.md)
13. [View Event](/02-analysis/usecases/docs/13-use-case-ViewEvent.md)
14. [Modify Event](/02-analysis/usecases/docs/14-use-case-ModifyEvent.md)
15. [Delete Event](/02-analysis/usecases/docs/15-use-case-DeleteEvent.md)
16. [List My Events ](/02-analysis/usecases/docs/16-use-case-ListMyEvents.md)
17. [List Tickets ](/02-analysis/usecases/docs/17-use-case-ListTickets.md)
18. [Buy Tickets ](/02-analysis/usecases/docs/18-use-case-BuyTickets.md)
19. [View Tickets ](/02-analysis/usecases/docs/19-use-case-ViewTickets.md)

## Class Diagram

The class diagram represents the information gained about the system by completing the use case realisations. 

![class diagram](/02-analysis/classdiagram/ClassDiagram.png)

## Description of Responsibilities

### Account
The `Account` class is an abstract entity class. It represents a user account in the system. The sole responsibility of account objects is to store the account's name, password, and email. To note, the email should be unique for every account.

### Administrator
The `Administrator` class is a concrete subclass of the `Account` class. It represents an administrator user in the system. The sole responsibility of administrator objects is to manage system-wide settings and user accounts.

### Organizer
The `Organizer` class is a concrete subclass of the `Account` class. It represents an event or a venue organizer in the system. The sole responsibility of organizer objects is to store extra information related to the organizer's company, phone number, and address. Organizers should be responsible for managing the events, venues, tickets that within their rights.

### User
The `User` class is a concrete subclass of the `Account` class. It represents a regular user in the system. The sole responsibility of user objects is to manage their tickets for events.

### Venue
The `Venue` class is a basic entity class. It represents a physical location where events are held. The sole responsibility of venue objects is to store details about the venue's name, address, total capacity, manager, and manager's phone number. To note, the name should be unique for every venue.

### Event
The `Event` class is a basic entity class. It represents an event scheduled at a venue. The sole responsibility of event objects is to store the event's name, description, date, start time, ending time, ticket type, and the number of available tickets (standing, seating, and premium). To note, the name should be unique for every event.

### Ticket
The `Ticket` class is a basic entity class. It represents a ticket purchased by a user for an event. The sole responsibility of ticket objects is to store the ticket type and admission details. To note, the admission should be unique for every ticket.

### EventController
The `EventController` class is a controller class. It is responsible for managing event-related operations, including adding, updating, confirming updates, deleting events, updating event schedules, and viewing event details.

### AccountController
The `AccountController` class is a controller class. It is responsible for managing account-related operations, including registering users, creating organizers and administrators, updating accounts, confirming updates, managing accounts, and viewing account details and histories.

### VenueController
The `VenueController` class is a controller class. It is responsible for managing venue-related operations, including adding, updating, confirming updates, managing venues, and viewing venue details.

### TicketController
The `TicketController` class is a controller class. It is responsible for managing ticket-related operations, including selling and buying tickets, updating ticket details, confirming updates, viewing ticket details, and viewing ticket history.

### VenueService
The `VenueService` class is a service class. It is responsible for performing operations related to venues, including adding, updating, deleting venues, retrieving venue details, and getting the schedule of events at a venue.

### EventService
The `EventService` class is a service class. It is responsible for performing operations related to events, including adding, updating, deleting events, and retrieving associated venues and organizers.

### AccountService
The `AccountService` class is a service class. It is responsible for performing operations related to accounts, including adding users, organizers, and administrators, updating accounts, deleting accounts, and retrieving account details and histories.

### TicketService
The `TicketService` class is a service class. It is responsible for performing operations related to tickets, including adding, deleting, updating, canceling tickets, and retrieving ticket details and histories.



## Milestone 2 Analysis

### Distribution of work on this milestone
#### Overall Distribution of Work
| Team Member | @Victoria | @Wenyi | @Kira210 | @sukikatte | @Yuki | @22207285 | @hs | @boran |
|-------------|-----------|--------|----------|------------|-------|-----------|-----|--------|
| Percentage  | 12%       | 12%    | 12%      | 12%        | 12%   | 12%       | 12% | 12%    |
#### Task Allocation
| Item                            | Contributor 1    | Contributor 2 | Reviewer      |
|---------------------------------|------------------|---------------|---------------|
| Description of Responsibilities | @Victoria(50%)   | @hs(50%)      | Other Members | 
| Class Diagram                   | @Victoria(50%)   | @hs(50%)      | Other Members | 
| Use Case 1: Create Account      | @Kira210(100%)   |               | Other Members | 
| Use Case 2: List Account        | @Kira210(100%)   |               | Other Members | 
| Use Case 3: View Account        | @Wenyi(100%)     |               | Other Members | 
| Use Case 4: Modify Account      | @Wenyi(100%)     |               | Other Members | 
| Use Case 5: Register Account    | @Wenyi(100%)     |               | Other Members | 
| Use Case 6: List Venues         | @sukikatte（100%） |               | Other Members | 
| Use Case 7: Add Venue           | @boran(100%)     |               | Other Members | 
| Use Case 8: View Venue          | @Kira210(100%)   |               | Other Members | 
| Use Case 9: Modify Venue        | @boran(100%)     |               | Other Members | 
| Use Case 10: "Delete Venue"     | @22207285 (100%) |               | Other Members | 
| Use Case 11: List Events        | @sukikatte（100%) |               | Other Members | 
| Use Case 12: Add Event          | @boran(100%)     |               | Other Members | 
| Use Case 13: "View Event"       | @22207285 (100%) |               | Other Members | 
| Use Case 14: Modify Event       | @boran(100%)     |               | Other Members | 
| Use Case 15: "Delete Event"     | @22207285 (100%) |               | Other Members | 
| Use Case 16: List My Event      | @sukikatte（100%) |               | Other Members | 
| Use Case 17: List Tickets       | @Yuki（100%)      |               | Other Members | 
| Use Case 18: Buy Tickets        | @Yuki（100%)      |               | Other Members | 
| Use Case 19: View Tickets       | @Yuki（100%)      |               | Other Members |  


#### Reflection Statements
| Team Member | Contribution Reflection Statement                                                                                                                                                                                                               |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @hs         | Participated in class diagramming and writing of description of responsibility. Overview other members' usecase.                                                                                                                                |
| @Boran      | Drew the sequence diagrams and wrote docs of use case 07, 09, 12 and 14 about its basic event and alternate events.                                                                                                                             |
| @sukikatte  | Drew the sequence diagramS and wrote docs of use case 06, 11 and 16 about its basic event and alternate events.                                                                                                                                 |
| @Kira210    | Drew the sequence diagramS and wrote docs of use case 01, 02 and 08 about its basic event and alternate events.                                                                                                                                 |
| @Yuki       | Drew the sequence diagramS and wrote docs of use case 17, 18 and 19 about its basic event and alternate events.                                                                                                                                 |
| @Wenyi      | Drew the sequence diagrams and wrote docs of use case 03, 04 and 05 about its basic event and alternate events.                                                                                                                                 |
| @Victoria   | Participated in class diagramming, writing and modifying the description of responsibility and modify the domain model diagram accordingly. Reviewing the work of other members and providing suggestions. Following up on the team's progress. |
| @22207285   | Drew the sequence diagrams and wrote docs of use case 10, 13 and 15 about its basic event and alternate events.                                                                                                                                 |

