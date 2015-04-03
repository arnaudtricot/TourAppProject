package com.application.tourapp.async;

import android.os.AsyncTask;

import com.application.tourapp.TypesActivity;
import com.application.tourapp.webservice.MessagesListWebService;
import com.application.tourapp.webservice.TypesListWebService;

/**
 * Le chargement des types au mode Async.
 * 
 * @author Islam
 * 
 */

public class TypesAsyncActivity extends AsyncTask<Void, Integer, Void> {

	TypesActivity typesAvtivity;

	public TypesAsyncActivity(TypesActivity typesAvtivity) {
		this.typesAvtivity = typesAvtivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}

}
