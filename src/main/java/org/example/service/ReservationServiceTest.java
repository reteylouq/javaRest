package org.example.service;

import org.example.model.Reservation;
import org.example.model.Table;
import org.example.repository.ReservationRepository;
import org.example.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation() {
        Long tableId = 1L;
        String customerName = "John Doe";
        LocalDateTime reservationTime = LocalDateTime.of(2024, 5, 30, 18, 0);

        Table table = new Table();
        table.setId(tableId);
        table.setNumber(1);
        table.setSeats(4);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setTable(table);
        reservation.setCustomerName(customerName);
        reservation.setReservationTime(reservationTime);

        when(tableRepository.findById(tableId)).thenReturn(Optional.of(table));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(tableId, customerName, reservationTime);

        assertNotNull(createdReservation);
        assertEquals(tableId, createdReservation.getTable().getId());
        assertEquals(customerName, createdReservation.getCustomerName());
        assertEquals(reservationTime, createdReservation.getReservationTime());
    }
}
