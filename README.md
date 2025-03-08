ShareIt: Place Rental Service

Overview

ShareIt is a microservices-based platform that allows users to rent and manage available places. Users can list their places for rent, book available places, and leave reviews after the rental period.

Technologies Used

Java 11+

Spring Boot (Spring Data JPA, Spring Web, Spring Security)

PostgreSQL (JPA + Hibernate)

Logging: SLF4J + Lombok

Object Mapping: MapStruct

Testing:

Unit tests: JUnit 5, Mockito

Integration tests: Spring Boot Test, MockMvc

Database testing: DataJpaTest + TestEntityManager

Microservices

1. User Service

Manages user accounts.

Endpoints:

POST /users – Create a new user.

PATCH /users/{id} – Update user information.

GET /users/{id} – Get user details.

GET /users – Get all users.

DELETE /users/{id} – Delete a user.

Tested with: UserControllerTest, UserServiceTest, UserRepositoryTest.

2. Booking Service

Handles place bookings.

Endpoints:

POST /bookings – Create a booking.

PATCH /bookings/{id} – Approve or reject a booking.

GET /bookings/{id} – Get booking details.

GET /bookings – Get all bookings.

GET /bookings/owner – Get bookings for the place owner.

Tested with: BookingControllerTest, BookingServiceTest, BookingRepositoryTest.

3. Place Service

Allows users to list, manage, and search for places.

Endpoints:

POST /places – Add a new place.

PATCH /places/{id} – Update a place.

GET /places/{id} – Get place details.

GET /places?search=keyword – Search for places.

POST /places/{id}/comment – Leave a review.

Tested with: ItemControllerTest, ItemServiceTest, ItemRepositoryTest.

4. Place Request Service

Allows users to request available places.

Endpoints:

POST /requests – Create a new place request.

GET /requests – Get user's requests.

GET /requests/{id} – Get request details.

GET /requests/all – View all requests.

Tested with: ItemRequestControllerTest, ItemRequestServiceTest, ItemRequestRepositoryTest.

Testing Coverage

✅ Controller Testing (MockMvc): UserControllerTest, ItemControllerTest, BookingControllerTest✅ Service Testing (Mockito): UserServiceTest, ItemServiceTest, BookingServiceTest✅ Repository Testing (DataJpaTest): UserRepositoryTest, ItemRepositoryTest, BookingRepositoryTest✅ Error Handling Tests: ExceptionControllerTest✅ Spring Boot Context Test: ShareItTests
