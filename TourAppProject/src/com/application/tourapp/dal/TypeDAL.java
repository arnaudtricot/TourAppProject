package com.application.tourapp.dal;

import java.util.ArrayList;

import android.content.Context;

import com.application.tourapp.databasehandler.TypesDatabaseHandler;
import com.application.tourapp.model.Type;

public class TypeDAL {
	
	TypesDatabaseHandler typesDB;
	
	public TypeDAL(Context context) {
		typesDB = new TypesDatabaseHandler(context);
	}
	
	public void addType(Type type) {
		typesDB.addType(type);
	}
	
	public ArrayList<Type> getAlltypes() {
		return typesDB.getAllTypes();
	}
	
	public Type getType(int id) {
		return typesDB.getType(id);
	}
	
	public void deleteAllTypes() {
		typesDB.deleteAllTypes();
	}

	public Type getTypeByNumRow(int i) {
		return typesDB.getTypeByNumRow(i);
	}

	public int getCountTypes() {
		return typesDB.getCountType();
	}

}
