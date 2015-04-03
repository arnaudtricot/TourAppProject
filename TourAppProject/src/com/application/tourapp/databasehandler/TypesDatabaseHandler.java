package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import com.application.tourapp.model.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TypesDatabaseHandler extends TourAppDatabaseHandler{

	public TypesDatabaseHandler(Context context) {
		super(context);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);
		
	}
	
	// Adding new type
    public void addType(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("test", Integer.toString(db.getVersion()));
        if(existsType(type) == false){
		    ContentValues values = new ContentValues();
		    values.put(KEY_ID_TYPE, type.getId()); 
		    values.put(KEY_NAME_TYPE, type.getName()); 
		    values.put(KEY_IMAGE_TYPE, type.getImageBlob()); 
		    db.insert(TABLE_TYPES, null, values);
        } else {
        	updaterType(type);
        }
        db.close(); 
    }
    
    public Type getType(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_TYPES, new String[] { KEY_ID_TYPE,
                KEY_NAME_TYPE, KEY_IMAGE_TYPE }, KEY_ID_TYPE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Type type = new Type();
        type.setId(Integer.parseInt(cursor.getString(0)));
        type.setName(cursor.getString(1));
        type.setImageBlob(cursor.getBlob(2));
        db.close();
        // return contact
        return type;
    }
    
    public boolean existsType(Type type) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	int id = type.getId();
    	
        Cursor cursor = db.query(TABLE_TYPES, new String[] { KEY_ID_TYPE,
                KEY_NAME_TYPE, KEY_IMAGE_TYPE }, KEY_ID_TYPE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public ArrayList<Type> getAllTypes() {
    	ArrayList<Type> typeList = new ArrayList<Type>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TYPES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setId(Integer.parseInt(cursor.getString(0)));
                type.setName(cursor.getString(1));
                type.setImageBlob(cursor.getBlob(2));
                // Adding contact to list
                typeList.add(type);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return typeList;
    }
    
    public void deleteAllTypes() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_TYPES, null, null);
    }
    
    public int updaterType(Type type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_TYPE, type.getName());
        values.put(KEY_IMAGE_TYPE, type.getImageBlob());
 
        // updating row
        db.update(TABLE_TYPES, values, KEY_ID_TYPE + " = ?",
                new String[] { String.valueOf(type.getId()) });
        db.close();
        return 1;
    }

	public Type getTypeByNumRow(int i) {
		Type type = new Type();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TYPES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToPosition(i);
 
        type.setId(Integer.parseInt(cursor.getString(0)));
        type.setName(cursor.getString(1));
        type.setImageBlob(cursor.getBlob(2));
        db.close();
		return type;
	}

	public int getCountType() {
		// Select All Query
        String selectQuery = "SELECT  count(*) FROM " + TABLE_TYPES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
	}

}
