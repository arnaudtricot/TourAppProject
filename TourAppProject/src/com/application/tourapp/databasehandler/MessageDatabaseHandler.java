package com.application.tourapp.databasehandler;

import java.util.ArrayList;

import com.application.tourapp.model.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MessageDatabaseHandler extends TourAppDatabaseHandler{

	public MessageDatabaseHandler(Context context) {
		super(context);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);
		
	}
	
	// Adding new type
    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(existsMessage(message) == false){
		    ContentValues values = new ContentValues();
		    values.put(KEY_ID_MESSAGE, message.getId()); 
		    values.put(KEY_SUBJECT_MESSAGE, message.getSubject()); 
		    values.put(KEY_TEXT_MESSAGE, message.getText());
		    db.insert(TABLE_MESSAGE, null, values);
        } else {
        	updateMessage(message);
        }
        db.close(); 
    }
    
    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_MESSAGE, new String[] { KEY_ID_MESSAGE,
                KEY_SUBJECT_MESSAGE, KEY_TEXT_MESSAGE }, KEY_ID_TI + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Message message = new Message();
        message.setId(Integer.parseInt(cursor.getString(0)));
        message.setSubject(cursor.getString(1));
        message.setText(cursor.getString(2));
        return message;
    }
    
    public boolean existsMessage(Message message) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	int id = message.getId();
    	
        Cursor cursor = db.query(TABLE_MESSAGE, new String[] { KEY_ID_MESSAGE,
                KEY_SUBJECT_MESSAGE, KEY_TEXT_MESSAGE }, KEY_ID_MESSAGE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public ArrayList<Message> getAllMessages() {
    	ArrayList<Message> messagesList = new ArrayList<Message>();

        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setId(Integer.parseInt(cursor.getString(0)));
                message.setSubject(cursor.getString(1));
                message.setText(cursor.getString(2));
                // Adding contact to list
                messagesList.add(message);
            } while (cursor.moveToNext());
        }

        return messagesList;
    }
    
    public void deleteAllMessges() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_MESSAGE, null, null);
    }
    
    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_MESSAGE, message.getSubject());
        values.put(KEY_TEXT_MESSAGE, message.getText()); 
	    
        // updating row
        return db.update(TABLE_MESSAGE, values, KEY_ID_MESSAGE + " = ?",
                new String[] { String.valueOf(message.getId()) });
    }

}
