package com.parkhere.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Base64;

import com.parkhere.entity.IConsumeParkingLots;
import com.parkhere.entity.ParkingLot;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParseConnector {

	private Context context;

	public ParseConnector(Context context) {
		this.context = context;
		Parse.initialize(context, "rHQ443EdBXs3eySuO5Dls9wA8abPBPWTnS5CUlNe", "CuKEnFSm9zFHiXJzzA10kxTB5o4yuozKBNlzRVtM");
	}
	
	public void getParkingLocations(final IConsumeParkingLots consumer, String rentTerm) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PARKING_LOT");
		query.selectKeys(Arrays.asList("LOCATION", "ADDRESS", "PRICE_PER_HOUR", "PRICE_PER_DAY", "NAME", "RENT_TERM"));
		if(rentTerm != null) {
			query.whereEqualTo("RENT_TERM", rentTerm);
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
				for(ParseObject object: objects) {
					ParkingLot parkingLot = buildParkingLot(object);
					parkingLots.add(parkingLot);
				}
				consumer.onReceiveParkingLots(parkingLots);
			}
		});
	}
	
	public void getParkingLocationById(final IConsumeParkingLots consumer, String objectId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PARKING_LOT");
		query.selectKeys(Arrays.asList("ObjectId","LOCATION", "ADDRESS", "PRICE_PER_HOUR", "PRICE_PER_DAY", "NAME", "RENT_TERM", "PICTURE_FILE", "DETAILS"));
		try {
			ParseObject parseObject = query.get(objectId);
			List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
			ParkingLot parkingLot = buildParkingLot(parseObject);
			ParseFile image = (ParseFile) parseObject.get("PICTURE_FILE");
			parkingLot.setImageBase64(Base64.encodeToString(image.getData(), Base64.NO_WRAP));
			parkingLot.setDetail((String) parseObject.get("DETAILS"));
			parkingLots.add(parkingLot);
			consumer.onReceiveParkingLots(parkingLots);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private ParkingLot buildParkingLot(
			ParseObject object) {
		ParseGeoPoint location = (ParseGeoPoint) object.get("LOCATION");
		String address = (String) object.get("ADDRESS");
		Number pricePerHour = (Number) object.get("PRICE_PER_HOUR");
		Number pricePerDay = (Number) object.get("PRICE_PER_DAY");
		
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setObjectId(object.getObjectId());
		parkingLot.setLatitude(location.getLatitude());
		parkingLot.setLongitude(location.getLongitude());
		parkingLot.setAddress(address);
		parkingLot.setPricePerDay(pricePerDay.doubleValue());
		parkingLot.setPricePerHour(pricePerHour.doubleValue());
		parkingLot.setName((String)object.get("NAME"));
		parkingLot.setRentTerm((String)object.get("RENT_TERM"));
		return parkingLot;
	}
}
