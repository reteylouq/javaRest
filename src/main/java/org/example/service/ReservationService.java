package org.example.service;

import org.example.model.Reservation;
import org.example.model.Table;
import org.example.repository.ReservationRepository;
import org.example.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    public Reservation createReservation(Long tableId, String customerName, LocalDateTime reservationTime) {
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RuntimeException("Table not found"));
        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setCustomerName(customerName);
        reservation.setReservationTime(reservationTime);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByReservationTimeBetween(start, end);
    }
}
