# Event Registration Platform — Backend

Spring Boot backend for an event registration platform.

## Features

- User registration
- User login
- Email existence check
- Create events
- List all events
- Join events
- Leave events
- View user registrations
- Count event participants
- Delete events by creator
- PostgreSQL database integration

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- REST API

## Database Model

Main entities:

- `UserModel`
- `EventModel`
- `RegistrationModel`

Relationships:

- One user can create many events
- One user can register for many events
- One event can have many registrations
- `RegistrationModel` connects users and events

## API Examples

```http
POST /api/v1/register
POST /api/v1/login
GET /api/v1/checkemail/{email}

GET /api/v1/events
POST /api/v1/events?creatorId={creatorId}
POST /api/v1/events/{eventId}/join?userId={userId}
DELETE /api/v1/events/{eventId}/leave?userId={userId}
GET /api/v1/events/my?userId={userId}
GET /api/v1/events/{eventId}/count
GET /api/v1/events/{eventId}/joined?userId={userId}
DELETE /api/v1/events/{eventId}?creatorId={creatorId}
```

## Backend

This backend connects to the frontend:

[pasakumi_frontend](https://github.com/kristapskomarovs-wq/pasakumi_frontend)

## Live Demo

https://komarovs.lv/

## Deployment

This project is deployed live together with the Angular frontend.

Deployment includes:

- Angular frontend build
- Spring Boot backend
- PostgreSQL database
- Nginx reverse proxy
- Custom domain setup
