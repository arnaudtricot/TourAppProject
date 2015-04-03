package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import com.application.tourapp.model.Design;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DesignDatabaseHandler extends TourAppDatabaseHandler {

	public DesignDatabaseHandler(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);

	}

	// Adding new type
	public void addDesign(Design design) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (existsDesign(design) == false) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID_DESIGN, design.getId());
			values.put(KEY_NAME_DESIGN, design.getName());
			values.put(KEY_TITLE_ITEM_DESIGN, design.getColorTitleItem());
			values.put(KEY_DESC_ITEM_DESIGN, design.getColorDescriptionItem());
			values.put(KEY_BACK_ITEM_DESIGN, design.getColorBackground());
			values.put(KEY_LOGO_DESIGN, design.getLogoApplication());
			db.insert(TABLE_DESIGN, null, values);
		} else {
			updaterDesign(design);
		}
		db.close();
	}

	public Design getDesign(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DESIGN, new String[] { KEY_ID_DESIGN,
				KEY_NAME_DESIGN, KEY_TITLE_ITEM_DESIGN, KEY_DESC_ITEM_DESIGN,
				KEY_BACK_ITEM_DESIGN, KEY_LOGO_DESIGN }, KEY_ID_DESIGN + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Design design = new Design();
		design.setId(Integer.parseInt(cursor.getString(0)));
		design.setName(cursor.getString(1));
		design.setColorTitleItem(cursor.getString(2));
		design.setColorDescriptionItem(cursor.getString(3));
		design.setColorBackground(cursor.getString(4));
		design.setLogoApplication(cursor.getBlob(5));
		// return contact
		return design;
	}

	public Design getDesignByName(String designName) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DESIGN, new String[] { KEY_ID_DESIGN,
				KEY_NAME_DESIGN, KEY_TITLE_ITEM_DESIGN, KEY_DESC_ITEM_DESIGN,
				KEY_BACK_ITEM_DESIGN, KEY_LOGO_DESIGN },
				KEY_NAME_DESIGN + "=?", new String[] { designName }, null,
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Design design = new Design();
		design.setId(Integer.parseInt(cursor.getString(0)));
		design.setName(cursor.getString(1));
		design.setColorTitleItem(cursor.getString(2));
		design.setColorDescriptionItem(cursor.getString(3));
		design.setColorBackground(cursor.getString(4));
		design.setLogoApplication(cursor.getBlob(5));

		db.close();

		// return contact
		return design;
	}

	public boolean existsDesign(Design design) {

		SQLiteDatabase db = this.getReadableDatabase();

		int id = design.getId();

		Cursor cursor = db.query(TABLE_DESIGN, new String[] { KEY_ID_DESIGN },
				KEY_ID_DESIGN + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Design> getAllDesign() {
		ArrayList<Design> designList = new ArrayList<Design>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_DESIGN;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Design design = new Design();
				design.setId(Integer.parseInt(cursor.getString(0)));
				design.setName(cursor.getString(1));
				design.setColorTitleItem((cursor.getString(2)));
				design.setColorDescriptionItem((cursor.getString(3)));
				design.setColorBackground(cursor.getString(4));
				design.setLogoApplication((cursor.getBlob(5)));
				designList.add(design);
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return designList;
	}

	public void deleteAllDesign() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_DESIGN, null, null);
	}

	public int updaterDesign(Design design) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_TYPE, design.getName());
		values.put(KEY_LOGO_DESIGN, design.getLogoApplication());
		values.put(KEY_TITLE_ITEM_DESIGN, design.getColorTitleItem());
		values.put(KEY_DESC_ITEM_DESIGN, design.getColorDescriptionItem());
		//values.put(KEY_BACK_ITEM_DESIGN, design.getColorDescriptionItem());
		// updating row
		db.update(TABLE_DESIGN, values, KEY_ID_DESIGN + " = ?",
				new String[] { String.valueOf(design.getId()) });
		db.close();
		return 1;
	}

}
