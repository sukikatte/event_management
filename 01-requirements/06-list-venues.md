# Use Case 06 - List Venues

## Description

Allows the administrator or organiser to view a list of all venues in the system available for all upcoming events

## Actors

Administrator, Organizer

## Triggers

This use case is triggered when the administrator or organizer needs to view a list of all venues in the system

## Preconditions

- The is on the administrator or organiser is on any page

## Postconditions

- The administrator is shown the list venues page (17-list-venues-admin) which shows all venues in the system
- The organiser is shown the list venues page (22-venues-organiser) which shows all venues in the system

## Courses of Events

### Basic Course of Events

1. The administrator selects the list venues functionality
2. The system displays the list venues page (17-list-venues-admin) which shows all venues in the system

### Basic Course of Events

1. The organiser selects the list venues functionality
2. The system displays the list venues page (22-venues-organiser) which shows all venues in the system

### Extension Points

None

## Inclusions

None

## Relevant UI Sketches
| Page Name                           | Image                                                 |
|-------------------------------------|-------------------------------------------------------|
| The List Venues Page(Administrator) | ![17-list-venues-admin](UI/17-list-venues-admin.png) |
| The List Venues Page(Organiser)     | ![22-venues-organiser](UI/22-venues-organiser.png)    |

## Data Outcomes
**READ** - All upcoming venues will be read and displayed
