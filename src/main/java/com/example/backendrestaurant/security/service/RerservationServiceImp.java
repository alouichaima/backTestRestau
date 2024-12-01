package com.example.backendrestaurant.security.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendrestaurant.dto.ReservationDto;
import com.example.backendrestaurant.models.Reservation;
import com.example.backendrestaurant.models.ReservationStatus;
import com.example.backendrestaurant.models.User;
import com.example.backendrestaurant.repository.ReservationRepository;
import com.example.backendrestaurant.repository.UserRepository;



@Service
public class RerservationServiceImp implements ReservationService{
	
	 @Autowired
	  private ReservationRepository reservationRepository;
	 
	 @Autowired
	  private UserRepository userRepository;



	 @Override
	 public Reservation postReservation(ReservationDto reservationDto) {
	     Long customerId = reservationDto.getCustomerId();

	     if (customerId != null) {
	         Optional<User> optionalUser = userRepository.findById(customerId);

	         if (optionalUser.isPresent()) {
	             Reservation reservation = new Reservation();
	             reservation.setTableType(reservationDto.getTableType());
	             reservation.setDateTime(reservationDto.getDateTime());
	             reservation.setDescription(reservationDto.getDescription());
	             reservation.setUser(optionalUser.get());
	             reservation.setReservationStatus(ReservationStatus.PENDING);

	             return reservationRepository.save(reservation);
	         }
	     }

	     return null;
	 }

	@Override
	public List<ReservationDto> getReservationsByUser(Long customerId) {
		return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

	@Override
	public List<ReservationDto> getReservations() {
		return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}

	@Override
	public ReservationDto changeReservationStatus(long reservationId, String status) {
		Optional<Reservation>optionalReservation= reservationRepository.findById(reservationId);
		if (optionalReservation.isPresent()) {
			Reservation existingReservation=optionalReservation.get();
			if (Objects.equals(status, "Approve")) {
				existingReservation.setReservationStatus(ReservationStatus.APPROVED);
			}
			else {
				existingReservation.setReservationStatus(ReservationStatus.DISAPPROVE);

			}
			Reservation updateReservation= reservationRepository.save(existingReservation);
			ReservationDto updaReservationDto = new ReservationDto();
			updateReservation.setId(updateReservation.getId());
			return updaReservationDto;
		}
		return null;
	}


}

