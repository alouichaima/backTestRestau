package com.example.backendrestaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backendrestaurant.dto.ReservationDto;
import com.example.backendrestaurant.models.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	List<Reservation> findAllByUserId(Long customerId);
	

	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationStatus = com.example.backendrestaurant.models.ReservationStatus.PENDING")
	public Long countPendingReservations();
	

	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationStatus = com.example.backendrestaurant.models.ReservationStatus.APPROVED")
	public Long countAPPROVEDReservations();
	
	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationStatus = com.example.backendrestaurant.models.ReservationStatus.DISAPPROVE")
	public Long countDISAPPROVEReservations();




}
