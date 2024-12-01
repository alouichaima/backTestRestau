package com.example.backendrestaurant.dto;


import java.util.Date;

import com.example.backendrestaurant.models.ReservationStatus;

public class ReservationDto {
	private Long id;
	private String tableType;
	private String description;
	private Date dateTime;
	private ReservationStatus reservationStatus  ;
	private Long customerId;
	private String customerName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Override
	public String toString() {
		return "ReservationDto [id=" + id + ", tableType=" + tableType + ", description=" + description + ", dateTime="
				+ dateTime + ", reservationStatus=" + reservationStatus + ", customerId=" + customerId + "]";
	}
	

	
	

}