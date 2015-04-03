package com.application.tourapp.async;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.application.tourapp.MessagesActivity;
import com.application.tourapp.baseadapter.MessageListBaseAdapter;
import com.application.tourapp.model.Message;
import com.application.tourapp.webservice.MessagesListWebService;

/**
 * Le chargement des messsages au mode Async.
 * 
 * @author Islam
 * 
 */

public class MessagesAsyncActivity extends AsyncTask<Void, Integer, Void> {

	MessagesActivity messagesAvtivity;

	public MessagesAsyncActivity(MessagesActivity messagesAvtivity) {
		this.messagesAvtivity = messagesAvtivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		new MessagesListWebService().fillMessages(messagesAvtivity
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
		ArrayList<Message> messagesDetails = messagesAvtivity
				.GetSearchResults(messagesAvtivity.getApplicationContext());
		messagesAvtivity.listMessages.setAdapter(new MessageListBaseAdapter(
				messagesAvtivity.getApplicationContext(), messagesDetails));
	}

}
