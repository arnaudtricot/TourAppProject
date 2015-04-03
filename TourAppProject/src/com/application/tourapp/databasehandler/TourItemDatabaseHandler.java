package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.Type;
import com.google.android.gms.internal.db;

public class TourItemDatabaseHandler extends TourAppDatabaseHandler {

	private Context context;

	public TourItemDatabaseHandler(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);

	}

	public void addTourItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean exist = existsTourItem(item);
		if (exist == false) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID_TI, item.getId());
			values.put(KEY_NAME_TI, item.getName());
			values.put(KEY_DESCRIPTION_TI, item.getDescription());
			values.put(KEY_EMAIL_TI, item.getEmail());
			values.put(KEY_ADDRESS_TI, item.getAddress());
			values.put(KEY_LAT_TI, item.getLatitude());
			values.put(KEY_LONG_TI, item.getLongitude());
			values.put(KEY_WEBSITE_TI, item.getWebsite());
			values.put(KEY_IMAGE_ONE_TI, item.getImageOne());
			values.put(KEY_IMAGE_TWO_TI, item.getImageTwo());
			values.put(KEY_IMAGE_THREE_TI, item.getImageThree());
			if (item.getType() != null) {
				values.put(KEY_TYPE_TI, item.getType().getId());
			} else {
				values.put(KEY_TYPE_TI, 0);
			}
			if (item.getCity() != null) {
				values.put(KEY_CITY_TI, item.getCity().getId());
			} else {
				values.put(KEY_CITY_TI, 0);
			}
			db.insert(TABLE_TI, null, values);
		} else {
			updateTourItem(item);
		}
		db.close();
	}

	public Item getTourItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TI, new String[] { KEY_ID_TI,
				KEY_NAME_TYPE, KEY_DESCRIPTION_TI, KEY_EMAIL_TI,
				KEY_ADDRESS_TI, KEY_LAT_TI, KEY_LONG_TI, KEY_WEBSITE_TI,
				KEY_IMAGE_ONE_TI, KEY_IMAGE_TWO_TI, KEY_IMAGE_THREE_TI,
				KEY_TYPE_TI, KEY_CITY_TI }, KEY_ID_TI + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		Item item = new Item();
		item.setId(Integer.parseInt(cursor.getString(0)));
		item.setName(cursor.getString(1));
		item.setDescription(cursor.getString(2));
		item.setEmail(cursor.getString(3));
		item.setAddress(cursor.getString(4));
		item.setLatitude(cursor.getString(5));
		item.setLongitude(cursor.getString(6));
		item.setWebsite(cursor.getString(7));
		item.setImageOne(cursor.getBlob(8));
		item.setImageTwo(cursor.getBlob(9));
		item.setImageThree(cursor.getBlob(10));
		if (!cursor.getString(11).equals("0")) {
			Type type = new TypeDAL(context).getType(Integer.parseInt(cursor
					.getString(11)));
			item.setType(type);
		}
		db.close();
		return item;
	}

	public boolean existsTourItem(Item item) {

		SQLiteDatabase db = this.getReadableDatabase();

		int id = item.getId();

		Cursor cursor = db.query(TABLE_TI, new String[] { KEY_ID_TI,
				KEY_NAME_TI, KEY_DESCRIPTION_TI }, KEY_ID_TI + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Item> getAllTourItemsByType(String typeIds, String cityIds) {
		ArrayList<Item> tourItemsList = new ArrayList<Item>();
		// Select All Query
		int typeId = Integer.parseInt(typeIds);
		int cityId = Integer.parseInt(cityIds);
		String selectQuery = "SELECT  * FROM " + TABLE_TI + " WHERE "
				+ KEY_TYPE_TI + " = " + "'" + typeId + "'" + "AND "
				+ KEY_CITY_TI + " = " + "'" + cityId + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = getTourItem(Integer.parseInt(cursor.getString(0)));
				/*
				 * item.setName(cursor.getString(1));
				 * item.setPrix(cursor.getString(3));
				 * item.setEmail(cursor.getString(5));
				 * item.setAddress(cursor.getString(6));
				 * item.setLongitude(cursor.getString(7));
				 * item.setLatitude(cursor.getString(8)); Type type = new
				 * TypeDAL(context).getType(Integer
				 * .parseInt(cursor.getString(9))); item.setType(type);
				 * item.setWebsite(cursor.getString(11));
				 * item.setDescription(cursor.getString(2));
				 * item.setImageOne(cursor.getBlob(12));
				 * item.setImageTwo(cursor.getBlob(13));
				 * item.setImageThree(cursor.getBlob(14));
				 */
				tourItemsList.add(item);
			} while (cursor.moveToNext());
		}
		// System.out.println("ALL TOUR ITEMS "+getAllTourItems().size());
		db.close();
		return tourItemsList;
	}
    
	
	// reccuperatiion de toutes les sous thèmes grace au thème
	
	public ArrayList<Item> getAllTourItemsByType(String typeIds) {
		ArrayList<Item> tourItemsList = new ArrayList<Item>();
		// Select All Query
		int typeId = Integer.parseInt(typeIds);
		
		String selectQuery = "SELECT  * FROM " + TABLE_TI + " WHERE "
				+ KEY_TYPE_TI + " = " + "'" + typeId + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = getTourItem(Integer.parseInt(cursor.getString(0)));
				/*
				 * item.setName(cursor.getString(1));
				 * item.setPrix(cursor.getString(3));
				 * item.setEmail(cursor.getString(5));
				 * item.setAddress(cursor.getString(6));
				 * item.setLongitude(cursor.getString(7));
				 * item.setLatitude(cursor.getString(8)); Type type = new
				 * TypeDAL(context).getType(Integer
				 * .parseInt(cursor.getString(9))); item.setType(type);
				 * item.setWebsite(cursor.getString(11));
				 * item.setDescription(cursor.getString(2));
				 * item.setImageOne(cursor.getBlob(12));
				 * item.setImageTwo(cursor.getBlob(13));
				 * item.setImageThree(cursor.getBlob(14));
				 */
				tourItemsList.add(item);
			} while (cursor.moveToNext());
		}
		// System.out.println("ALL TOUR ITEMS "+getAllTourItems().size());
		db.close();
		return tourItemsList;
	}
	
	
	// reccuperation de toutes les Items de la base
	

	public ArrayList<Item> getAllTourItems() {
		ArrayList<Item> tourItemsList = new ArrayList<Item>();

		String selectQuery = "SELECT  * FROM " + TABLE_TI;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				item.setId(Integer.parseInt(cursor.getString(0)));
				item.setName(cursor.getString(1));
				item.setPrix(cursor.getString(2));
				item.setEmail(cursor.getString(4));
				item.setAddress(cursor.getString(5));
				item.setLongitude(cursor.getString(6));
				item.setLatitude(cursor.getString(7));
				Type type = new TypeDAL(context).getType(Integer
						.parseInt(cursor.getString(8)));
				item.setType(type);
				item.setWebsite(cursor.getString(9));
				item.setDescription(cursor.getString(10));
				item.setImageOne(cursor.getBlob(11));
				item.setImageTwo(cursor.getBlob(12));
				item.setImageThree(cursor.getBlob(13));
				// Adding contact to list
				tourItemsList.add(item);
			} while (cursor.moveToNext());
		}
		db.close();
		return tourItemsList;
	}

	public void deleteAllTourItems() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TI, null, null);
	}

	public int updateTourItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_TI, item.getName());
		values.put(KEY_NAME_TI, item.getName());
		values.put(KEY_DESCRIPTION_TI, item.getDescription());
		values.put(KEY_EMAIL_TI, item.getEmail());
		values.put(KEY_ADDRESS_TI, item.getAddress());
		values.put(KEY_LAT_TI, item.getLatitude());
		values.put(KEY_LONG_TI, item.getLongitude());
		values.put(KEY_WEBSITE_TI, item.getWebsite());
		values.put(KEY_IMAGE_ONE_TI, item.getImageOne());
		values.put(KEY_IMAGE_TWO_TI, item.getImageTwo());
		values.put(KEY_IMAGE_THREE_TI, item.getImageThree());
		if (item.getType() != null) {
			values.put(KEY_TYPE_TI, item.getType().getId());
		}
		if (item.getCity() != null) {
			values.put(KEY_CITY_TI, item.getCity().getId());
		}
		// updating row

		db.update(TABLE_TI, values, KEY_ID_TI + " = ?",
				new String[] { String.valueOf(item.getId()) });
		db.close();
		return 1;
	}

	public void deleteTourItemsForTypeAndCity(String typeId, String cityId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TI, KEY_TYPE_TI + " = " + "'" + typeId + "'" + "AND "
				+ KEY_CITY_TI + " = " + "'" + cityId + "'", null);
	}
}
