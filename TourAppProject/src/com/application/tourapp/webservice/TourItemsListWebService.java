package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.City;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.Type;
import com.application.tourapp.xml.XMLParser;

public class TourItemsListWebService {
	static final String URL = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/"
			+ TourAppGlobal.WEB_SERVICE_ITEM_CITY_TYPE;
	static final String URL_ITEM = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/"
			+ TourAppGlobal.WEB_SERVICE_TOUR_ITEM;
	static final String URL_IMAGE_ONE = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/" + TourAppGlobal.IT_IMAGE_ONE;
	static final String URL_IMAGE_TWO = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/" + TourAppGlobal.IT_IMAGE_TWO;
	static final String URL_IMAGE_THREE = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/" + TourAppGlobal.IT_IMAGE_THREE;
	static final String KEY_TI = "models.ToursicItem";
	static final String KEY_NAME = "name";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_PRIX = "prix";
	static final String KEY_CITY = "city";
	static final String KEY_COORDS = "coords";
	static final String KEY_EMAIL = "email";
	static final String KEY_ADDRESS = "address";
	static final String KEY_LONG = "longitude";
	static final String KEY_LAT = "latitude";
	static final String KEY_TYPE = "type";
	static final String KEY_WEBSITE = "website";
	static final String KEY_TYPE_NAME = "name";
	static final String KEY_ID = "id";

	public ArrayList<Item> listItems = new ArrayList<Item>();

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données des items touristiques par rapport au type d'activité et à la ville
	 * 
	 */
	public void fillTourItemsByTypeAndCity(String typeId, String cityId,
			Context context) {
		try {
			listItems.clear();
			XMLParser parser = new XMLParser();
			String urlWebServices = URL;
			urlWebServices += "/" + cityId + "-" + typeId;
			TourItemDAL tourItemDAL = new TourItemDAL(context);
			TypeDAL typeDAL = new TypeDAL(context);
			CityDAL cityDAL = new CityDAL(context);
			String filePath = TourAppGlobal.ITEMS_LOCAL_FOLDER
					+ TourAppGlobal.ITEMS_LOCAL_NAME + "_" + typeId
					+ TourAppGlobal.XML_EXTNSION;
			boolean connected = TourAppGlobal.isOnline(context);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = parser.getXmlFromUrl(urlWebServices);
				TourAppGlobal.writeFile(context, xmlToSave, filePath);

				String xml = TourAppGlobal.readFile(context, filePath);
				Document doc = parser.getDomElement(xml);
				NodeList itemsNodeList = doc.getElementsByTagName(KEY_TI);

				for (int i = 0; i < itemsNodeList.getLength(); i++) {
					Element itemElement = (Element) itemsNodeList.item(i);
					Item tourisItem = new Item();
					tourisItem.setId(Integer.parseInt(parser.getValue(
							itemElement, KEY_ID)));
					tourisItem.setName(parser.getValue(itemElement, KEY_NAME));
					tourisItem.setDescription(parser.getValue(itemElement,
							KEY_DESCRIPTION));
					tourisItem.setPrix(parser.getValue(itemElement, KEY_PRIX));
					tourisItem.setImageOne(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_ONE + "/"
									+ tourisItem.getId()));
					tourisItem.setImageTwo(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_TWO + "/"
									+ tourisItem.getId()));
					tourisItem.setImageThree(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_THREE + "/"
									+ tourisItem.getId()));
					Type type = typeDAL.getType(Integer.parseInt(typeId));
					tourisItem.setType(type);
					City city = cityDAL.getCity(Integer.parseInt(cityId));
					tourisItem.setCity(city);
					NodeList coordsNodeList = itemElement
							.getElementsByTagName(KEY_COORDS);
					for (int y = 0; y < coordsNodeList.getLength(); y++) {
						Element coordsElement = (Element) coordsNodeList
								.item(y);
						tourisItem.setEmail(parser.getValue(coordsElement,
								KEY_EMAIL));
						tourisItem.setAddress(parser.getValue(coordsElement,
								KEY_ADDRESS));
						tourisItem.setWebsite(parser.getValue(coordsElement,
								KEY_WEBSITE));
						tourisItem.setLongitude(parser.getValue(coordsElement,
								KEY_LONG));
						tourisItem.setLatitude(parser.getValue(coordsElement,
								KEY_LAT));
					}
					// tourisItem.setImageNumber(1);
					// listItems.add(tourisItem);
					tourItemDAL.addTourItem(tourisItem);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception here ... " + e.getMessage());
		}

	}

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données des items touristiques par rapport au type d'activité
	 * 
	 */
	public void fillTourItemsByType(String typeId, Context context) {
		try {
			listItems.clear();
			XMLParser parser = new XMLParser();
			String urlWebServices = URL;
			urlWebServices += "/" + typeId;
			TourItemDAL tourItemDAL = new TourItemDAL(context);
			TypeDAL typeDAL = new TypeDAL(context);
			CityDAL cityDAL = new CityDAL(context);
			String filePath = TourAppGlobal.ITEMS_LOCAL_FOLDER
					+ TourAppGlobal.ITEMS_LOCAL_NAME + "_" + typeId
					+ TourAppGlobal.XML_EXTNSION;
			boolean connected = TourAppGlobal.isOnline(context);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = parser.getXmlFromUrl(urlWebServices);
				TourAppGlobal.writeFile(context, xmlToSave, filePath);

				String xml = TourAppGlobal.readFile(context, filePath);
				Document doc = parser.getDomElement(xml);
				NodeList itemsNodeList = doc.getElementsByTagName(KEY_TI);

				for (int i = 0; i < itemsNodeList.getLength(); i++) {
					Element itemElement = (Element) itemsNodeList.item(i);
					Item tourisItem = new Item();
					tourisItem.setId(Integer.parseInt(parser.getValue(
							itemElement, KEY_ID)));
					tourisItem.setName(parser.getValue(itemElement, KEY_NAME));
					tourisItem.setDescription(parser.getValue(itemElement,
							KEY_DESCRIPTION));
					tourisItem.setPrix(parser.getValue(itemElement, KEY_PRIX));
					tourisItem.setImageOne(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_ONE + "/"
									+ tourisItem.getId()));
					tourisItem.setImageTwo(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_TWO + "/"
									+ tourisItem.getId()));
					tourisItem.setImageThree(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_THREE + "/"
									+ tourisItem.getId()));
					Type type = typeDAL.getType(Integer.parseInt(typeId));
					tourisItem.setType(type);
					NodeList citiesNodeList = itemElement
							.getElementsByTagName(KEY_CITY);
					City city = cityDAL.getCity(Integer.parseInt(parser
							.getValue(((Element) citiesNodeList.item(0)),
									KEY_ID)));
					tourisItem.setCity(city);
					NodeList coordsNodeList = itemElement
							.getElementsByTagName(KEY_COORDS);
					for (int y = 0; y < coordsNodeList.getLength(); y++) {
						Element coordsElement = (Element) coordsNodeList
								.item(y);
						tourisItem.setEmail(parser.getValue(coordsElement,
								KEY_EMAIL));
						tourisItem.setAddress(parser.getValue(coordsElement,
								KEY_ADDRESS));
						tourisItem.setWebsite(parser.getValue(coordsElement,
								KEY_WEBSITE));
						tourisItem.setLongitude(parser.getValue(coordsElement,
								KEY_LONG));
						tourisItem.setLatitude(parser.getValue(coordsElement,
								KEY_LAT));
					}
					// tourisItem.setImageNumber(1);
					// listItems.add(tourisItem);
					tourItemDAL.addTourItem(tourisItem);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception here ... " + e.getMessage());
		}
	}

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données des items touristiques
	 * 
	 */
	public void fillTourItems(Context context) {
		try {
			listItems.clear();
			XMLParser parser = new XMLParser();
			String urlWebServices = URL_ITEM;
			TourItemDAL tourItemDAL = new TourItemDAL(context);
			TypeDAL typeDAL = new TypeDAL(context);
			CityDAL cityDAL = new CityDAL(context);
			String filePath = TourAppGlobal.ITEMS_LOCAL_FOLDER
					+ TourAppGlobal.ITEMS_LOCAL_NAME
					+ TourAppGlobal.XML_EXTNSION;
			boolean connected = TourAppGlobal.isOnline(context);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = parser.getXmlFromUrl(urlWebServices);
				TourAppGlobal.writeFile(context, xmlToSave, filePath);

				String xml = TourAppGlobal.readFile(context, filePath);
				Document doc = parser.getDomElement(xml);
				NodeList itemsNodeList = doc.getElementsByTagName(KEY_TI);

				for (int i = 0; i < itemsNodeList.getLength(); i++) {
					Element itemElement = (Element) itemsNodeList.item(i);
					Item tourisItem = new Item();
					tourisItem.setId(Integer.parseInt(parser.getValue(
							itemElement, KEY_ID)));
					tourisItem.setName(parser.getValue(itemElement, KEY_NAME));
					tourisItem.setDescription(parser.getValue(itemElement,
							KEY_DESCRIPTION));
					tourisItem.setPrix(parser.getValue(itemElement, KEY_PRIX));
					tourisItem.setImageOne(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_ONE + "/"
									+ tourisItem.getId()));
					tourisItem.setImageTwo(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_TWO + "/"
									+ tourisItem.getId()));
					tourisItem.setImageThree(TourAppGlobal
							.getImageFromUrl(URL_IMAGE_THREE + "/"
									+ tourisItem.getId()));
					NodeList typesNodeList = itemElement
							.getElementsByTagName(KEY_TYPE);
					Type type = null;
					for (int j = 0; j < typesNodeList.getLength(); j++) {
						Element typeList = (Element) typesNodeList.item(j);
						if (typeList.getElementsByTagName(KEY_ID).item(0) != null) {
							type = typeDAL
									.getType(Integer.parseInt(parser.getValue(
											((Element) typeList), KEY_ID)));
						}
					}
					tourisItem.setType(type);
					NodeList citiesNodeList = itemElement
							.getElementsByTagName(KEY_CITY);
					City city = cityDAL.getCity(Integer.parseInt(parser
							.getValue(((Element) citiesNodeList.item(0)),
									KEY_ID)));
					tourisItem.setCity(city);
					NodeList coordsNodeList = itemElement
							.getElementsByTagName(KEY_COORDS);
					for (int y = 0; y < coordsNodeList.getLength(); y++) {
						Element coordsElement = (Element) coordsNodeList
								.item(y);
						tourisItem.setEmail(parser.getValue(coordsElement,
								KEY_EMAIL));
						tourisItem.setAddress(parser.getValue(coordsElement,
								KEY_ADDRESS));
						tourisItem.setWebsite(parser.getValue(coordsElement,
								KEY_WEBSITE));
						tourisItem.setLongitude(parser.getValue(coordsElement,
								KEY_LONG));
						tourisItem.setLatitude(parser.getValue(coordsElement,
								KEY_LAT));
					}
					// tourisItem.setImageNumber(1);
					// listItems.add(tourisItem);
					tourItemDAL.addTourItem(tourisItem);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception here ... " + e.getMessage());
		}
	}
}
