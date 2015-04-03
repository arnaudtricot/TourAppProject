package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.tourapp.model.City;

public class CityDatabaseHandler extends TourAppDatabaseHandler{

	public CityDatabaseHandler(Context context) {
		super(context);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
	}
	
	// Adding new type
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(existsCity(city) == false){
		    ContentValues values = new ContentValues();
		    values.put(KEY_ID_CITY, city.getId()); 
		    values.put(KEY_NAME_CITY, city.getName()); 
		    values.put(KEY_SELECT_CITY, city.isSelected());
		    db.insert(TABLE_CITY, null, values);
        } else {
        	updaterCity(city);
        }
        db.close(); 
    }
    
    public City getCity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CITY, new String[] { KEY_ID_CITY,
                KEY_NAME_CITY, KEY_SELECT_CITY }, KEY_ID_CITY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        City city = new City();
        city.setId(Integer.parseInt(cursor.getString(0)));
        city.setName(cursor.getString(1));
        city.setSelected((cursor.getInt(2) == 1));
        // return contact
        return city;
    }
    
    public boolean existsCity(City city) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	int id = city.getId();
    	
        Cursor cursor = db.query(TABLE_CITY, new String[] { KEY_ID_CITY,
                KEY_NAME_CITY }, KEY_ID_CITY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public ArrayList<City> getAllCities() {
    	ArrayList<City> citiesList = new ArrayList<City>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CITY;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(Integer.parseInt(cursor.getString(0)));
                city.setName(cursor.getString(1));
                city.setSelected((cursor.getInt(2) == 1));
                citiesList.add(city);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return citiesList;
    }
    
    public void deleteAllCities() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_CITY, null, null);
    }
    
    public int updaterCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_CITY, city.getName());
        values.put(KEY_SELECT_CITY, city.isSelected());
        // updating row
        return db.update(TABLE_CITY, values, KEY_ID_CITY + " = ?",
                new String[] { String.valueOf(city.getId()) });
    }

}
