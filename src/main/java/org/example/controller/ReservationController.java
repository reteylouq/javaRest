package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.model.Reservation;
import org.example.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@Api(value = "Reservation Management System", description = "Operations pertaining to reservations in Restaurant Reservation System")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @ApiOperation(value = "Create a new reservation", response = Reservation.class)
    public Reservation createReservation(@RequestParam Long tableId, @RequestParam String customerName, @RequestParam String reservationTime) {
        LocalDateTime time = LocalDateTime.parse(reservationTime);
        return reservationService.createReservation(tableId, customerName, time);
    }

    @GetMapping
    @ApiOperation(value = "Get all reservations within a time range", response = List.class)
    public List<Reservation> getReservations(@RequestParam String start, @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return reservationService.getReservations(startTime, endTime);
    }
}
