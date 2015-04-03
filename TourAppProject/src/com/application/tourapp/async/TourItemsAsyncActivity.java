package com.application.tourapp.async;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.application.tourapp.TouristicItemsActivity;
import com.application.tourapp.baseadapter.ItemListBaseAdapter;
import com.application.tourapp.model.Item;
import com.application.tourapp.webservice.TourItemsListWebService;

/**
 * Le chargement des items au mode Async.
 * 
 * @author Islam
 * 
 */


public class TourItemsAsyncActivity extends AsyncTask<Void, Integer, Void>{

	TouristicItemsActivity tourItemsAvtivity;
	String typeId;
	String cityId;
	public TourItemsAsyncActivity(TouristicItemsActivity tourItemsAvtivity, String typeId, String cityId) {
		this.tourItemsAvtivity = tourItemsAvtivity;
		this.typeId = typeId;
		this.cityId = cityId;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		new TourItemsListWebService().fillTourItemsByTypeAndCity(typeId, cityId, tourItemsAvtivity.getApplicationContext());
		return null;
		
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		ArrayList<Item> tourItemsDetails = tourItemsAvtivity.GetSearchResults(typeId, cityId, tourItemsAvtivity.getApplicationContext());
		tourItemsAvtivity.listItems.setAdapter(new ItemListBaseAdapter(tourItemsAvtivity, tourItemsDetails));
		System.out.println("done async tour items list");
	}

}
