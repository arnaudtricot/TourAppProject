package com.application.tourapp.dal;

import android.content.Context;

import com.application.tourapp.databasehandler.DesignDatabaseHandler;
import com.application.tourapp.model.Design;

public class DesignDAL {
	
	DesignDatabaseHandler designDB;
	
	public DesignDAL(Context context) {
		designDB = new DesignDatabaseHandler(context);
	}
	
	public void addDesign(Design design) {
		designDB.addDesign(design);
	}
	
	public Design getDesign(int id) {
		return designDB.getDesign(id);
	}
	
	public Design getDesignByName(String designName) {
		return designDB.getDesignByName(designName);
	}
	
	public void deleteAllDesign() {
		designDB.deleteAllDesign();
	}

}
