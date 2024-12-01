package com.example.backendrestaurant.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendrestaurant.dto.ReservationDto;
import com.example.backendrestaurant.models.Reservation;
import com.example.backendrestaurant.repository.ReservationRepository;
import com.example.backendrestaurant.security.service.ReservationService;

import jakarta.validation.ValidationException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReservationController {
	  @Autowired
	  private ReservationService reservationService;
	  
		@Autowired
		private ReservationRepository reservationRepository;


	
	  @PostMapping("/client/reservation")
	  public ResponseEntity<?> postReservation(@RequestBody ReservationDto reservationDto) {
	      try {
	          Reservation postedReservation = reservationService.postReservation(reservationDto);
	          return ResponseEntity.status(HttpStatus.CREATED).body(postedReservation);
	      } catch (ValidationException e) {
	          return ResponseEntity.badRequest().body(e.getMessage());
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	      }
	  }
	  
	  @GetMapping("/client/reservations/{customerId}")
	  public ResponseEntity<List<ReservationDto>>getReservationsByUser(@PathVariable Long customerId){
		  List<ReservationDto>reservationDtoList=reservationService.getReservationsByUser(customerId);
		  if (reservationDtoList == null) return ResponseEntity.notFound().build();
		  return ResponseEntity.ok(reservationDtoList) ;
	  }

	  
	  @GetMapping("/admin/reservations")
	  public ResponseEntity<List<ReservationDto>>getReservations(){
		  List<ReservationDto>reservationDtoList=reservationService.getReservations();
		  if (reservationDtoList == null) return ResponseEntity.notFound().build();
		  return ResponseEntity.ok(reservationDtoList) ;
	  }
	  
	  @GetMapping("/admin/reservation/{reservationId}/{status}")
	  public ResponseEntity<ReservationDto>changeReservationStatus(@PathVariable long reservationId , @PathVariable String status){
		  ReservationDto updateReservationDto=reservationService.changeReservationStatus(reservationId ,status);
		  if (updateReservationDto == null) return ResponseEntity.notFound().build();
		  return ResponseEntity.ok(updateReservationDto) ;
	  }
	  @GetMapping("/CountPendingReservations")
	  @ResponseBody
	  public Long countPendingReservations() {
	      return reservationRepository.countPendingReservations();
	  }
	  
	  @GetMapping("/CountAPPROVEDReservations")
	  @ResponseBody
	  public Long countAPPROVEDReservations() {
	      return reservationRepository.countAPPROVEDReservations();
	  }
	  	  
	  @GetMapping("/CountDISAPPROVEReservations")
	  @ResponseBody
	  public Long countDISAPPROVEReservations() {
	      return reservationRepository.countDISAPPROVEReservations();
	  }
	
	

}

