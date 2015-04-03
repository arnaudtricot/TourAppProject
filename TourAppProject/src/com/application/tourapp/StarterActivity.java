package com.application.tourapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.application.tourapp.async.MainLoaderAsyncActivity;
import com.application.tourapp.databasehandler.TourAppDatabaseHandler;
import com.application.tourapp.global.TourAppGlobal;

/**
 * L'activite qui demare tout
 * 
 * @author Ramy
 * 
 */

@SuppressLint("SdCardPath")
@SuppressWarnings("deprecation")
public class StarterActivity extends GenericActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.starter_activity);

		SQLiteDatabase checkDatabase = null;
		try {
			checkDatabase = SQLiteDatabase.openDatabase(
					"/data/data/com.application.tourapp/databases/tourapp",
					null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			checkDatabase = null;
		}
		if (!TourAppGlobal.isOnline(this) && checkDatabase == null) {
			TourAppDatabaseHandler dbh = new TourAppDatabaseHandler(this.getApplicationContext());
			SQLiteDatabase db = dbh.getWritableDatabase();
			
			try {
				String fileToInsert = "insertbdd.txt";
				InputStream input = this.getApplicationContext().getAssets().open(fileToInsert);
				
				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				String line;
				
				br = new BufferedReader(new InputStreamReader(input));
				while ((line = br.readLine()) != null) {
					db.execSQL(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (TourAppGlobal.isOnline(this)) {
				TourAppGlobal.IS_CONNECT = true;
			}
		}
		new MainLoaderAsyncActivity(this).execute();
	}
}
