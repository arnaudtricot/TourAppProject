package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.model.Favorites;
import com.application.tourapp.model.Item;

public class FavoritesDatabaseHandler extends TourAppDatabaseHandler {

	private Context context;

	public FavoritesDatabaseHandler(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);
	}

	// Adding new type
	public void addFavorit(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (existsFavorit(item) == false) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID_FAV, item.getId());
			db.insert(TABLE_FAV, null, values);
		} else {
			deleteFavorit(item);
		}
		db.close();
	}

	/*
	 * public Message getMessage(int id) { SQLiteDatabase db =
	 * this.getReadableDatabase();
	 * 
	 * Cursor cursor = db.query(TABLE_FAV, new String[] { KEY_ID_FAV },
	 * KEY_ID_FAV + "=?", new String[] { String.valueOf(id) }, null, null, null,
	 * null); if (cursor != null) cursor.moveToFirst();
	 * 
	 * Message message = new Message();
	 * message.setId(Integer.parseInt(cursor.getString(0)));
	 * message.setSubject(cursor.getString(1));
	 * message.setText(cursor.getString(2)); return message; }
	 */

	public boolean existsFavorit(Item item) {

		SQLiteDatabase db = this.getReadableDatabase();

		int id = item.getId();

		Cursor cursor = db.query(TABLE_FAV, new String[] { KEY_ID_FAV },
				KEY_ID_FAV + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteFavorit(Item item) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(TABLE_FAV, KEY_ID_FAV + "=" + String.valueOf(item.getId()), null);
	}

	public Favorites getAllFavorites() {
		ArrayList<Item> itemList = new ArrayList<Item>();

		Favorites favorites = new Favorites();

		favorites.setListFavorits(itemList);

		String selectQuery = "SELECT  * FROM " + TABLE_FAV;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Item item = new TourItemDAL(context).getTourItem(Integer
						.parseInt(cursor.getString(0)));
				favorites.getListFavorits().add(item);
			} while (cursor.moveToNext());
		}

		return favorites;
	}

	public void deleteAllFavorites() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FAV, null, null);
	}

	/*
	 * public int updateMessage(Message message) { SQLiteDatabase db =
	 * this.getWritableDatabase();
	 * 
	 * ContentValues values = new ContentValues();
	 * values.put(KEY_SUBJECT_MESSAGE, message.getSubject());
	 * values.put(KEY_TEXT_MESSAGE, message.getText());
	 * 
	 * // updating row return db.update(TABLE_MESSAGE, values, KEY_ID_MESSAGE +
	 * " = ?", new String[] { String.valueOf(message.getId()) }); }
	 */

}
