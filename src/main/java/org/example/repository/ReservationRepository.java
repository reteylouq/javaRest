package org.example.repository;

import org.example.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationTimeBetween(LocalDateTime start, LocalDateTime end);
}
