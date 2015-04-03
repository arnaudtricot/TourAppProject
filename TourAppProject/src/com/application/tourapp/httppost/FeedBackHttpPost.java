package com.application.tourapp.httppost;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.StrictMode;

import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.FeedBack;

public class FeedBackHttpPost {
	
	private static String URL_FEEDBACK = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.FEEDBACK_ADD;
			
	
	/**
	 * Permet de récupérer les messages sur le site web
	 * 
	 * @param feedBack Objet représentant un message
	 * @return La connexion a-t-elle pu être effectuée ?
	 */
	public boolean postFeedback(FeedBack feedBack) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy); 
			String urlPostFb = URL_FEEDBACK + "/" + feedBack.getMessage();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String formatedURL = TourAppGlobal.formatURL(urlPostFb);
			HttpPost httpPost = new HttpPost(formatedURL);
	
			HttpResponse httpResponse = httpClient.execute(httpPost);
			httpResponse.getEntity();
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

}
