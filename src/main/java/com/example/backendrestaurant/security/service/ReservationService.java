package com.example.backendrestaurant.security.service;

import java.util.List;

import com.example.backendrestaurant.dto.ReservationDto;
import com.example.backendrestaurant.models.Reservation;

public interface ReservationService {
	Reservation postReservation(ReservationDto reservationDto);

	List<ReservationDto> getReservationsByUser(Long customerId);


	List<ReservationDto> getReservations();

	ReservationDto changeReservationStatus(long reservationId, String status);

}
