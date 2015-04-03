package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.City;
import com.application.tourapp.xml.XMLParser;

public class CityListWebService {
	static final String URL = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/"
			+ TourAppGlobal.WEB_SERVICE_CITIES;
	static final String KEY_NAME = "name";
	static final String KEY_ID = "id";
	static final String KEY_CITY = "models.City";

	public ArrayList<City> listCities = new ArrayList<City>();

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données sur les villes recencées
	 * 
	 */
	public void fillListCity(Context context) {
		try {
			listCities.clear();
			XMLParser parser = new XMLParser();
			CityDAL cityDAL = new CityDAL(context);
			boolean connected = TourAppGlobal.isOnline(context);
			System.out.println("The network status is : " + connected);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = null;

				TourAppGlobal.writeFile(context, xmlToSave,
						TourAppGlobal.TYPES_LOCAL_FOLDER
								+ TourAppGlobal.TYPES_LOCAL_NAME);

				String xml = TourAppGlobal.readFile(context,
						TourAppGlobal.TYPES_LOCAL_FOLDER
								+ TourAppGlobal.TYPES_LOCAL_NAME);
				Document doc = parser.getDomElement(xml);

				System.out.println("Root element : "
						+ doc.getDocumentElement().getNodeName());

				NodeList listCitiesNode = doc.getElementsByTagName(KEY_CITY);

				if (listCitiesNode.getLength() != 0) {
					cityDAL.deleteAllCities();
				}

				for (int i = 0; i < listCitiesNode.getLength(); i++) {
					Element typeElement = (Element) listCitiesNode.item(i);

					City city = new City();

					String nameCity = parser.getValue(typeElement, KEY_NAME);
					int idCity = Integer.parseInt(parser.getValue(typeElement,
							KEY_ID));
					boolean selected = false;
					if (i == 0) {
						selected = true;
					}

					city.setName(nameCity);
					city.setId(idCity);
					city.setSelected(selected);
					cityDAL.addCity(city);
				}
			}
		} catch (Exception e) {
			System.out.println("Problem while parsing or saving XML : "
					+ e.getMessage());
		}
	}
}
