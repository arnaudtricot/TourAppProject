package com.application.tourapp.dal;

import java.util.ArrayList;

import android.content.Context;

import com.application.tourapp.databasehandler.CityDatabaseHandler;
import com.application.tourapp.model.City;

public class CityDAL {
	
	CityDatabaseHandler cityDB;
	
	public CityDAL(Context context) {
		cityDB = new CityDatabaseHandler(context);
	}
	
	public void addCity(City city) {
		cityDB.addCity(city);
	}
	
	public ArrayList<City> getAllCities() {
		return cityDB.getAllCities();
	}
	
	public City getCity(int id) {
		return cityDB.getCity(id);
	}
	
	public void deleteAllCities() {
		cityDB.deleteAllCities();
	}

}
