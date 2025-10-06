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

# Design

This phase continues the development process by performing further realisations on the courses of events that we described in the analysis phase. This requires the creation of new sequence diagrams for each course of events. These diagrams will be more detailed in that they will contain all parameter and type infromation. Additionally, the diagrams will now include the UI and database components of the system.

Decisions made while constructing these diagrams are also documented in the class diagram.

## Use Case Realisations (Sequence Diagrams)

Each use case contains the separate sequence diagrams for each course of events. The diagrams are stored in the `images` folder and referenced in the markdown files.

1. [Creat Account](/03-design/usecases/docs/01-use-case-CreateAccount.md)
2. [List Account](/03-design/usecases/docs/02-use-case-ListAccount.md)
3. [View Account](/03-design/usecases/docs/03-use-case-ViewAccount.md)
4. [Modify Account](/03-design/usecases/docs/04-use-case-ModifyAccount.md)
5. [Register Account](/03-design/usecases/docs/05-use-case-RegisterAccount.md)
6. [List Venues](/03-design/usecases/docs/06-use-case-ListVenues.md)
7. [Add Venue](/03-design/usecases/docs/07-use-case-AddVenue.md)
8. [View Venue](/03-design/usecases/docs/08-use-case-ViewVenue.md)
9. [Modify Venue](/03-design/usecases/docs/09-use-case-ModifyVenue.md)
10. [Delete Venue](/03-design/usecases/docs/10-use-case-DeleteVenue.md)
11. [List Events](/03-design/usecases/docs/11-use-case-ListEvents.md)
12. [Add Event](/03-design/usecases/docs/12-use-case-AddEvent.md)
13. [View Event](/03-design/usecases/docs/13-use-case-ViewEvent.md)
14. [Modify Event](/03-design/usecases/docs/14-use-case-ModifyEvent.md)
15. [Delete Event](/03-design/usecases/docs/15-use-case-DeleteEvent.md)
16. [List My Events ](/03-design/usecases/docs/16-use-case-ListMyEvents.md)
17. [List Tickets ](/03-design/usecases/docs/17-use-case-ListTickets.md)
18. [Buy Tickets ](/03-design/usecases/docs/18-use-case-BuyTickets.md)
19. [View Tickets ](/03-design/usecases/docs/19-use-case-ViewTickets.md)

## Class Diagram

The class diagram represents the information gained about the system by completing the use case realisations. 

![class diagram](/03-design/classdiagram/ClassDiagram.png)

## Data Persistence
Within the EventManagementSystem the following classes should be maintained between executions.
1. Account - name, email, password
- User: extend Account
- Organizer: extend Account, company, phoneNumber, address
- Administrator: extend Account
2. Event: name, description, date, startTime, endTime, standingNumberAvailable, seatingNumberAvailable, premiumNumberAvailable
3. Ticket: event, user, ticketType, admission
4. Venue: name, address, capacity, managerName, managerPhoneNumber, managerEmail

### Relationships
1. Event - Organizer (many to many)
2. Ticket - User (many to 1)
3. Event - Venue (many to 1)
4. Venue - Organizer (many to many)

## Milestone 3 Design

### Distribution of work on this milestone
#### Task Allocation
| Item                            | Contributor 1    | Contributor 2 | Contributor 3 |
|---------------------------------|------------------|---------------|---------------|
| Reviewer                        | @Victoria(45%)   | @hs(45%)      | Other Members(10%) | 
| Class Diagram                   | @Victoria(50%)   | @hs(50%)      |  | 
| Use Case 1: Create Account      | @Kira210(100%)   |               |  | 
| Use Case 2: List Account        | @Kira210(100%)   |               |  | 
| Use Case 3: View Account        | @Wenyi(100%)     |               |  | 
| Use Case 4: Modify Account      | @hs(100%)        |               |  | 
| Use Case 5: Register Account    | @Wenyi(100%)     |               |  | 
| Use Case 6: List Venues         | @sukikatte（100%) |              |  | 
| Use Case 7: Add Venue           | @boran(100%)     |               |  | 
| Use Case 8: View Venue          | @Kira210(100%)   |               |  | 
| Use Case 9: Modify Venue        | @Victoria(100%)  |               |  | 
| Use Case 10: Delete Venue       | @22207285 (100%) |               |  | 
| Use Case 11: List Events        | @sukikatte（100%)|               |  | 
| Use Case 12: Add Event          | @boran(100%)     |               |  | 
| Use Case 13: View Event         | @22207285 (100%) |               |  | 
| Use Case 14: Modify Event       | @sukikatte(60%)  | @boran (40%)               |  | 
| Use Case 15: Delete Event       | @22207285 (100%) |               |  | 
| Use Case 16: List My Event      | @sukikatte（100%)|               |  | 
| Use Case 17: List Tickets       | @Yuki（100%)     |               |  | 
| Use Case 18: Buy Tickets        | @Yuki（100%)     |               |  | 
| Use Case 19: View Tickets       | @Yuki（100%)     |               |  |  


#### Reflection Statements
| Team Member | Contribution Reflection Statement                                                                                                                                                                                          |
|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @hs      | Drew the sequence diagrams and wrote docs of use case 04 about its basic event and alternate. Participate in drawing class diagram. Review all the diagrams in the docs.                                                   |
| @Wenyi   | Wrote docs of use case 03 & 05 about basic events and alternates.                                                                                                                                                          |
| @Victoria | Drew the sequence diagrams and wrote docs of use case 09 about its basic event and alternate. Participate in drawing class diagram. Review all the diagrams in the docs and give detailed and consistent feedback to each. |
| @kira210 | Wrote docs of use case 01 & 02 & 08 about basic events and alternates.                                                                                                                                                     | 
| @Yuki    | Wrote docs of use case 17 & 18 & 19 about basic events and alternates.                                                                                                                                                     |
| @boran     | Wrote docs of use case 07 & 12 & 14(40%) about basic events and alternates.                                                                                                                                                |
| @sukikatte     | Wrote docs of use case 06 & 11 & 14(60%) &16 about basic events and alternates.                                                                                                                                            |
| @22207285      | Wrote docs of use case 10 & 13 & 15 about basic events and alternates.                                                                                                                                                     |

