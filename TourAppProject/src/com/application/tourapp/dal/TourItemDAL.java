package com.application.tourapp.dal;

import java.util.ArrayList;

import android.content.Context;

import com.application.tourapp.databasehandler.TourItemDatabaseHandler;
import com.application.tourapp.model.Item;

public class TourItemDAL {
	
	TourItemDatabaseHandler tourItemsDB;
	
	public TourItemDAL(Context context) {
		tourItemsDB = new TourItemDatabaseHandler(context);
	}
	
	public void addTourItem(Item item) {
		tourItemsDB.addTourItem(item);
	}
	
	public ArrayList<Item> getAllTourItemsByType(String type, String city) {
		return tourItemsDB.getAllTourItemsByType(type, city);
	}
	
	public void deleteAllTourItems() {
		tourItemsDB.deleteAllTourItems();
	}
	public ArrayList<Item> getAllTourItems(){
		return tourItemsDB.getAllTourItems();
	}
	
	public ArrayList<Item> getAllTourItemsByTypeOnly(String type){
		return tourItemsDB.getAllTourItemsByType(type);
	}
	
	public Item getTourItem(int id) {
		return tourItemsDB.getTourItem(id);
	}

	public void deleteTourItemsForTypeAndCity(String typeId, String cityId) {
		tourItemsDB.deleteTourItemsForTypeAndCity(typeId, cityId);
	}

}
