package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Type;
import com.application.tourapp.xml.XMLParser;

public class TypesListWebService {
	static final String URL = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/"
			+ TourAppGlobal.WEB_SERVICE_TYPES;
	static final String URL_IMAGE = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/" + TourAppGlobal.TYPE_IMAGE;
	static final String KEY_NAME = "name";
	static final String KEY_ID = "id";
	static final String KEY_TYPE = "models.Type";
	static final String KEY_IMAGE = "image";
	static final String KEY_BLOB = "UUID";

	public ArrayList<Type> listTypes = new ArrayList<Type>();

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données sur les types d'activités disponibles
	 * 
	 */
	public void fillListType(Context context) {
		try {
			listTypes.clear();
			XMLParser parser = new XMLParser();
			TypeDAL typeDAL = new TypeDAL(context);
			boolean connected = TourAppGlobal.isOnline(context);
			System.out.println("The network status is : " + connected);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = parser.getXmlFromUrl(URL);
				TourAppGlobal.writeFile(context, xmlToSave,
						TourAppGlobal.TYPES_LOCAL_FOLDER
								+ TourAppGlobal.TYPES_LOCAL_NAME);

				String xml = TourAppGlobal.readFile(context,
						TourAppGlobal.TYPES_LOCAL_FOLDER
								+ TourAppGlobal.TYPES_LOCAL_NAME);
				Document doc = parser.getDomElement(xml);

				System.out.println("Root element : "
						+ doc.getDocumentElement().getNodeName());

				NodeList listTypesNode = doc.getElementsByTagName(KEY_TYPE);
				if (listTypesNode.getLength() != 0) {
					typeDAL.deleteAllTypes();
				}

				for (int i = 0; i < listTypesNode.getLength(); i++) {
					Element typeElement = (Element) listTypesNode.item(i);

					Type type = new Type();

					String nameType = parser.getValue(typeElement, KEY_NAME);
					int idType = Integer.parseInt(parser.getValue(typeElement,
							KEY_ID));

					String imageURL = URL_IMAGE + "/"
							+ Integer.toString(idType);

					type.setName(nameType);
					type.setId(idType);
					type.setImageBlob(TourAppGlobal.getImageFromUrl(imageURL));

					typeDAL.addType(type);
					new TourItemsListWebService().fillTourItemsByType(
							Integer.toString(type.getId()), context);
				}
			}
		} catch (Exception e) {
			System.out.println("Problem while parsing or saving XML : "
					+ e.getMessage());
		}
	}
}
