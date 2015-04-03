package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.TakeMeTourDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.TakeMeTour;
import com.application.tourapp.xml.XMLParser;

public class TakeMeTourListWebService {
	static final String URL = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.WEB_SERVICE_TMT;
	static final String URL_IMAGE_ONE = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.IT_IMAGE_ONE;
	static final String URL_IMAGE_TWO = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.IT_IMAGE_TWO;
	static final String URL_IMAGE_THREE = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.IT_IMAGE_THREE;
	static final String KEY_TMT = "models.TakeMeTour";
	static final String KEY_MORNING = "morningItem";
	static final String KEY_AFTER = "afternoonItem";
	static final String KEY_NIGHT = "nightItem";
	static final String KEY_NAME = "name";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_PRIX = "prix";
	static final String KEY_COORDS = "coords";
	static final String KEY_EMAIL = "email";
	static final String KEY_ADDRESS = "address";
	static final String KEY_LONG = "longitude";
	static final String KEY_LAT = "latitude";
	static final String KEY_TYPE = "type";
	static final String KEY_WEBSITE = "website";
	static final String KEY_TYPE_NAME = "name";
	static final String KEY_ID = "id";
	
	public XMLParser parser = new XMLParser();
	
	public ArrayList<Item> listItems = new ArrayList<Item>();
	
	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données sur les propositions d'activités
	 * 
	 */
	public ArrayList<Item> fillTakeMeTour(Context context) {
		try{
			listItems.clear();
			String urlWebServices = URL;
			TourItemDAL tourItemDAL = new TourItemDAL(context);
			TypeDAL typeDAL = new TypeDAL(context);
			TakeMeTourDAL takeMeTourDAL = new TakeMeTourDAL(context);
			boolean connected = TourAppGlobal.isOnline(context);
			if(connected && TourAppGlobal.IS_CONNECT) { 			
				String xmlToSave = parser.getXmlFromUrl(urlWebServices);
				Document doc = parser.getDomElement(xmlToSave);
				NodeList tmtNodeList = doc.getElementsByTagName(KEY_TMT);
				if (tmtNodeList.getLength() != 0) {
					takeMeTourDAL.deleteAllTakeMeTour();
				}
				for (int i = 0; i < tmtNodeList.getLength(); i++) {
					Element tmtElement = (Element) tmtNodeList.item(i);
					TakeMeTour takeMeTour = new TakeMeTour();
					
					NodeList itemMorningNodeList = tmtElement.getElementsByTagName(KEY_MORNING);
					Item morningItem = addTakeMeTourFromXml(itemMorningNodeList, typeDAL, tourItemDAL);
					
					NodeList itemAfterNodeList = tmtElement.getElementsByTagName(KEY_AFTER);
					Item afterItem = addTakeMeTourFromXml(itemAfterNodeList, typeDAL, tourItemDAL);
					
					NodeList itemNightNodeList = tmtElement.getElementsByTagName(KEY_NIGHT);
					Item nightItem = addTakeMeTourFromXml(itemNightNodeList, typeDAL, tourItemDAL);
					
					takeMeTour.setId(Integer.parseInt(parser.getValue(tmtElement, KEY_ID)));
					takeMeTour.setMorningItem(morningItem);
					takeMeTour.setAfternonItem(afterItem);
					takeMeTour.setNightItem(nightItem);
					
					takeMeTourDAL.addTakeMeTour(takeMeTour);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception here ... "+e.getMessage());
		}
		return listItems;
	}
	
	private Item addTakeMeTourFromXml(NodeList itemsNodeList, TypeDAL typeDAL, TourItemDAL tourItemDAL) {
		Item tourisItem = new Item();
		for (int x = 0; x < itemsNodeList.getLength(); x++) {
			Element itemElement = (Element) itemsNodeList.item(x);
			tourisItem.setId(Integer.parseInt(parser.getValue(itemElement, KEY_ID)));
			tourisItem.setName(parser.getValue(itemElement, KEY_NAME));
			tourisItem.setDescription(parser.getValue(itemElement, KEY_DESCRIPTION));
			tourisItem.setPrix(parser.getValue(itemElement, KEY_PRIX));
			tourisItem.setImageOne(TourAppGlobal.getImageFromUrl(URL_IMAGE_ONE+ "/" + tourisItem.getId()));
			tourisItem.setImageTwo(TourAppGlobal.getImageFromUrl(URL_IMAGE_TWO+ "/" + tourisItem.getId()));
			tourisItem.setImageThree(TourAppGlobal.getImageFromUrl(URL_IMAGE_THREE+ "/" + tourisItem.getId()));
			NodeList coordsNodeList = itemElement.getElementsByTagName(KEY_COORDS);
			for (int y = 0; y < coordsNodeList.getLength(); y++) {
				Element coordsElement = (Element) coordsNodeList.item(y);
				tourisItem.setEmail(parser.getValue(coordsElement, KEY_EMAIL));
				tourisItem.setAddress(parser.getValue(coordsElement, KEY_ADDRESS));
				tourisItem.setWebsite(parser.getValue(coordsElement, KEY_WEBSITE));
				tourisItem.setLongitude(parser.getValue(coordsElement, KEY_LONG));
				tourisItem.setLatitude(parser.getValue(coordsElement, KEY_LAT));
			}
			tourItemDAL.addTourItem(tourisItem);
		}
		return tourisItem;
	}
}
