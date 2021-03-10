package com.bike.dto;

public class BikeDto {
	
	private String name;
	private String addr;
	private Double latitude;
	private Double longitude;
	private int bike_count;
	
	public BikeDto() {
		
	}
	
	public BikeDto(String name, String addr, Double latitude, Double longitude, int bike_count) {
		this.name = name;
		this.addr = addr;
		this.latitude = latitude;
		this.longitude = longitude;
		this.bike_count = bike_count;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public int getBike_count() {
		return bike_count;
	}


	public void setBike_count(int bike_count) {
		this.bike_count = bike_count;
	}
}
