### Use Case 18 - Buy Tickets

## Description

The user can purchase tickets by selecting date, time, price, and quantity

## Actors

User

## Triggers

The user intends to buy tickets for an upcoming event.

## Preconditions

- The user must view the event details (10-buy-tickets)
Sufficient tickets should still be available for purchase

## Postconditions

- Upon selecting date, time, price, and quantity, the user needs to proceed by clicking the "Pay" button

## Courses of Events

### Basic Course of Events

1. The user selects options like date, time, price, and ticket quantity
2. The system notifies the user that tickets are currently available
3. If tickets are available, the user can click the "Pay" button to make a purchase

### Alternative Courses of Events - Tickets Not Available

1. No available tickets match the user's chosen criteria
2. The system notifies the user that no tickets are currently available

### Extension Points

None

## Relevant UI Sketches
| Page Name               | Image                                      |
|-------------------------|--------------------------------------------|
| Buy Tickets Page | ![Buy Tickets Page](UI/10-buy-tickets.png) |

## Inclusions
Upcoming event ticket purchasing.

## Data Outcomes
**UPDATE** - Save updated ticket information in the system.
