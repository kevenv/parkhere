package com.parkhere.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ParkingLot implements Serializable{
	@Override
	public String toString() {
		return "ParkingLot [latitude=" + latitude + ", longitude=" + longitude
				+ ", address=" + address + ", pricePerHour=" + pricePerHour
				+ ", pricePerDay=" + pricePerDay + ", rentTerm=" + rentTerm
				+ ", name=" + name + ", objectId=" + objectId + "]";
	}

	private double latitude;
	private double longitude;
	private String address;
	private double pricePerHour;
	private double pricePerDay;
	private String rentTerm;
	private String name;
	private String objectId;
	private String imageBase64;
	private String detail;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public double getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public String getRentTerm() {
		return rentTerm;
	}
	public void setRentTerm(String rentTerm) {
		this.rentTerm = rentTerm;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("name", getName());
			jsonObject.put("address", getAddress());
			jsonObject.put("pricePerDay", getPricePerDay());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	public static ParkingLot fromJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			ParkingLot parkingLot = new ParkingLot();
			parkingLot.setAddress(jsonObject.getString("address"));
			parkingLot.setName(jsonObject.getString("name"));
			parkingLot.setPricePerDay(jsonObject.getDouble("pricePerDay"));
			return parkingLot;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
