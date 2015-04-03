package com.application.tourapp.databasehandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.TakeMeTour;

public class TakeMeTourDatabaseHandler extends TourAppDatabaseHandler{

	private Context context;
	
	public TakeMeTourDatabaseHandler(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onCreate(db);
		
	}
	
	public void addTakeMeTour(TakeMeTour takeMeTour) {
        SQLiteDatabase db = this.getWritableDatabase();
        //deleteAllTakeMeTour();
        if(existsTakeMeTour(takeMeTour) == false){
		    ContentValues values = new ContentValues();
		    values.put(KEY_ID_TMT, takeMeTour.getId());
		    values.put(KEY_MORNING_TMT, takeMeTour.getMorningItem().getId()); 
		    values.put(KEY_AFTER_TMT, takeMeTour.getAfternoonItem().getId()); 
		    values.put(KEY_NIGHT_TMT, takeMeTour.getNightItem().getId());
		    db.insert(TABLE_TMT, null, values);
        } else {
        	updateTakeMeTour(takeMeTour);
        }
        db.close(); 
    }
    
    public boolean existsTakeMeTour(TakeMeTour takeMeTour) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	int id = takeMeTour.getId();
    	
        Cursor cursor = db.query(TABLE_TMT, new String[] { KEY_ID_TMT }, KEY_ID_TMT + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public int updateTakeMeTour(TakeMeTour takeMeTour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MORNING_TMT, takeMeTour.getMorningItem().getId());
        values.put(KEY_AFTER_TMT, takeMeTour.getAfternoonItem().getId());
        values.put(KEY_NIGHT_TMT, takeMeTour.getNightItem().getId());
	    
        // updating row
        return db.update(TABLE_TMT, values, KEY_ID_TMT + " = ?",
                new String[] { String.valueOf(takeMeTour.getId()) });
    }
    
    public TakeMeTour getTakeMeTour() {
    	TakeMeTour takeMeTour= new TakeMeTour();
        String selectQuery = "SELECT  * FROM " + TABLE_TMT ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	TourItemDAL  tourItemDAL = new TourItemDAL(context);
                Item itemMorning = tourItemDAL.getTourItem((Integer.parseInt(cursor.getString(1))));
                Item itemAfter = tourItemDAL.getTourItem(Integer.parseInt(cursor.getString(2)));
                Item itemNight = tourItemDAL.getTourItem(Integer.parseInt(cursor.getString(3)));
                takeMeTour.setId(Integer.parseInt(cursor.getString(0)));
                takeMeTour.setMorningItem(itemMorning);
                takeMeTour.setAfternonItem(itemAfter);
                takeMeTour.setNightItem(itemNight);
            } while (cursor.moveToNext());
        }
        db.close();
        return takeMeTour;
    }
    
    public void deleteAllTakeMeTour() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete * from "+ TABLE_TMT);
    }

}
