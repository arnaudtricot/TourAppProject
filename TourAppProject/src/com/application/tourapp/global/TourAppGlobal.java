package com.application.tourapp.global;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Une classe globale pour la gestion de toutes les actiona et les varaibles
 * commune entre les transaction de l'application TourAPP
 * 
 * @author Islam
 * 
 */

public class TourAppGlobal {
	public static String HOST_NAME = "http://miage.herokuapp.com";
	//public static String HOST_NAME = "http://192.168.3.113:9000";
	public static String WEB_SERVICE = "webservice";
	public static String WEB_SERVICE_CITIES = "cities.xml";
	public static String WEB_SERVICE_TYPES = "types.xml";
	public static String WEB_SERVICE_TOUR_ITEMS = "touritems.xml";
	public static String WEB_SERVICE_TOUR_ITEM = "touritems.xml";
	public static String WEB_SERVICE_TMT = "tmt.xml";
	public static String WEB_SERVICE_MESSAGES = "messages.xml";
	public static String WEB_SERVICE_LOGO = "logo";
	public static String WEB_SERVICE_DESING = "design.xml";
	public static String WEB_SERVICE_ITEM_CITY_TYPE = "itemscitytype.xml";
	public static String TYPES_LOCAL_FOLDER = "types";
	public static String TYPES_LOCAL_NAME = "types.xml";
	public static String ITEMS_LOCAL_FOLDER = "touritems";
	public static String ITEMS_LOCAL_NAME = "touritems";
	public static String XML_EXTNSION = ".xml";
	public static String FEEDBACK_ADD = "addfeedback";
	public static String TYPE_IMAGE = "type/image";
	public static String IT_IMAGE_ONE = "imageitemone";
	public static String IT_IMAGE_TWO = "imageitemtwo";
	public static String IT_IMAGE_THREE = "imageitemthree";
	public static String ERGO_APPLICATION = "Ergonomie";
	public static boolean IS_CONNECT = false;
	public static int INDICE_TYPE = 0;

	/**
	 * Retourne le statut de connexion de l'utilisateur
	 * 
	 */
	public static boolean isConnectingToInternet(Context applicationContext) {
		ConnectivityManager connectivity = (ConnectivityManager) applicationContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
		}
		return false;
	}

	/**
	 * Retourne le statut de connexion de l'utilisateur
	 * 
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/**
	 * Permet l'écriture dans un fichier
	 * 
	 */
	public static void writeFile(Context context, String data, String filename)
			throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
		osw = new OutputStreamWriter(fos);
		osw.write(data);
		osw.close();
		fos.close();
	}

	/**
	 * Permet de lire dans un fichier
	 * 
	 */
	public static String readFile(Context context, String file)
			throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		String data = null;
		fis = context.openFileInput(file);
		isr = new InputStreamReader(fis);
		char[] inputBuffer = new char[fis.available()];
		isr.read(inputBuffer);
		data = new String(inputBuffer);
		isr.close();
		fis.close();
		return data;
	}

	/**
	 * Permet de passer une chaine de caractère au format URL
	 * 
	 */
	public static String formatURL(String URL) {
		return URL.replaceAll(" ", "%20");
	}

	/**
	 * Récupère une image à partir d'un URL
	 * 
	 */
	public static byte[] getImageFromUrl(String url) {
		try {
			URL imageUrl = new URL(url);
			URLConnection ucon = imageUrl.openConnection();

			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			return baf.toByteArray();
		} catch (Exception e) {
			Log.d("ImageManager", "Error: " + e.toString());
		}
		return null;
	}
	
	/**
	 * Vérifie s'il s'agit d'une image
	 * 
	 */
	public static boolean checkIsImage(byte[] blobImage) {
		return false;
	}
}
