package com.parkhere.entity;

import java.util.List;

public interface IConsumeParkingLots {
	
	public void onReceiveParkingLots(List<ParkingLot> parkingLots);
}
