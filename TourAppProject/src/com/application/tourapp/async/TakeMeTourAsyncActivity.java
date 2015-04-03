package com.application.tourapp.async;

import android.os.AsyncTask;

import com.application.tourapp.TmtActivity;
import com.application.tourapp.webservice.TakeMeTourListWebService;

/**
 * Le chargement de tmt au mode Async.
 * 
 * @author Ramy
 * 
 */


public class TakeMeTourAsyncActivity extends AsyncTask<Void, Integer, Void>{

	TmtActivity tmtActivity;
	
	public TakeMeTourAsyncActivity(TmtActivity tmtActivity) {
		this.tmtActivity = tmtActivity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		new TakeMeTourListWebService().fillTakeMeTour(tmtActivity.getApplicationContext());
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		tmtActivity.init();
	}

}
