package com.application.tourapp.dal;

import android.content.Context;

import com.application.tourapp.databasehandler.TakeMeTourDatabaseHandler;
import com.application.tourapp.model.TakeMeTour;

public class TakeMeTourDAL {
	
	TakeMeTourDatabaseHandler takeMeTourDB;
	
	public TakeMeTourDAL(Context context) {
		takeMeTourDB = new TakeMeTourDatabaseHandler(context);
	}
	
	public void addTakeMeTour(TakeMeTour takeMeTour) {
		takeMeTourDB.addTakeMeTour(takeMeTour);
	}
	
	public TakeMeTour getTakeMeTour() {
		return takeMeTourDB.getTakeMeTour();
	}
	
	public void deleteAllTakeMeTour() {
		takeMeTourDB.deleteAllTakeMeTour();
	}
}
