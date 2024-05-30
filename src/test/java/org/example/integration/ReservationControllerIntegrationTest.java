package org.example.integration;

import org.example.model.Reservation;
import org.example.repository.ReservationRepository;
import org.example.repository.TableRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
        tableRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateReservationSuccess() {
        // Given
        Long tableId = createTable(1, 4);

        // When & Then
        webTestClient.mutateWith(SecurityMockServerConfigurers.csrf())
                .post().uri(uriBuilder -> uriBuilder
                        .path("/reservations")
                        .queryParam("tableId", tableId)
                        .queryParam("customerName", "John Doe")
                        .queryParam("reservationTime", "2024-05-30T18:00:00")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reservation.class)
                .value(reservation -> {
                    assert reservation.getTable().getId().equals(tableId);
                    assert reservation.getCustomerName().equals("John Doe");
                    assert reservation.getReservationTime().equals(LocalDateTime.of(2024, 5, 30, 18, 0));
                });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCreateReservationFailureTableNotFound() {
        // When & Then
        webTestClient.mutateWith(SecurityMockServerConfigurers.csrf())
                .post().uri(uriBuilder -> uriBuilder
                        .path("/reservations")
                        .queryParam("tableId", 999L)
                        .queryParam("customerName", "John Doe")
                        .queryParam("reservationTime", "2024-05-30T18:00:00")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private Long createTable(int number, int seats) {
        return tableRepository.save(new org.example.model.Table(number, seats)).getId();
    }
}
