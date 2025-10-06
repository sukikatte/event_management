### Use Case 17 - List Tickets

## Description 

  The user can view all records of both past and upcoming events they have purchased.
## Actors

  User 
## Triggers   

  The user wants to review their past or upcoming event purchases.

## Preconditions  

- The user must be logged in (03-main-user)
- The event must exist in the system and have been purchased by the user.

## Postconditions

- None

## Courses of Events

### Basic Course of Events

1. The user selects “Tickets” from the home page menu.
2. The system displays all tickets for events purchased by the user (past and future).

### Extension Points

- None

## Relevant UI Sketches
| Page Name               | Image                                        |
|-------------------------|----------------------------------------------|
| Main User Page          | ![Main User Page](UI/03-main-user.png)       |
| Tickets User Page | ![Tickets User Page](UI/09-tickets-user.png) |


## Inclusions
  Event listing and detail view.

## Data Outcomes
**READ** - Event details for past and upcoming events that the user has purchased.
