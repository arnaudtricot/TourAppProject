package com.application.tourapp.async;

import android.content.Intent;
import android.os.AsyncTask;

import com.application.tourapp.MainActicvity;
import com.application.tourapp.StarterActivity;
import com.application.tourapp.webservice.CityListWebService;
import com.application.tourapp.webservice.DesignWebService;
import com.application.tourapp.webservice.MessagesListWebService;
import com.application.tourapp.webservice.TourItemsListWebService;
import com.application.tourapp.webservice.TypesListWebService;

/**
 * Le chargement initial
 * 
 * @author Islam
 * 
 */

public class MainLoaderAsyncActivity extends AsyncTask<Void, Integer, Void> {

	StarterActivity mainAvtivity;

	public MainLoaderAsyncActivity(StarterActivity mainAvtivity) {
		this.mainAvtivity = mainAvtivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		new TypesListWebService().fillListType(mainAvtivity
				.getApplicationContext());
		new MessagesListWebService().fillMessages(mainAvtivity
				.getApplicationContext());
		new DesignWebService().fillDesign(mainAvtivity.getApplicationContext());
		new CityListWebService().fillListCity(mainAvtivity
				.getApplicationContext());
		new TourItemsListWebService().fillTourItems(mainAvtivity
				.getApplicationContext());
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		// ArrayList<Message> messagesDetails =
		// mainAvtivity.GetSearchResults(mainAvtivity.getApplicationContext());
		// mainAvtivity.listMessages.setAdapter(new
		// MessageListBaseAdapter(mainAvtivity.getApplicationContext(),
		// messagesDetails));
		Intent MainScreenIntent = new Intent(
				mainAvtivity.getApplicationContext(), MainActicvity.class);
		mainAvtivity.startActivity(MainScreenIntent);
	}

}
