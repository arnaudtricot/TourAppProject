package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Type;
import com.application.tourapp.xml.XMLParser;

public class DesignWebService {
	static final String URL = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/"
			+ TourAppGlobal.WEB_SERVICE_DESING;
	static final String URL_IMAGE = TourAppGlobal.HOST_NAME + "/"
			+ TourAppGlobal.WEB_SERVICE + "/" + TourAppGlobal.WEB_SERVICE_LOGO;
	static final String KEY_NAME = "designName";
	static final String KEY_ID = "id";
	static final String KEY_DESIGN = "models.Design";
	static final String KEY_TITLE_ITEM = "colorTitleItem";
	static final String KEY_DESC_ITEM = "colorDescriptionItem";
	static final String KEY_BACK_ITEM = "colorBackground";

	public ArrayList<Type> listTypes = new ArrayList<Type>();

	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données sur le design
	 * 
	 */
	public void fillDesign(Context context) {
		try {
			listTypes.clear();
			XMLParser parser = new XMLParser();
			DesignDAL designDAL = new DesignDAL(context);
			boolean connected = TourAppGlobal.isOnline(context);
			System.out.println("The network status is : " + connected);
			if (connected && TourAppGlobal.IS_CONNECT) {
				String xmlToSave = parser.getXmlFromUrl(URL);

				Document doc = parser.getDomElement(xmlToSave);

				System.out.println("Root element : "
						+ doc.getDocumentElement().getNodeName());

				NodeList listTypesNode = doc.getElementsByTagName(KEY_DESIGN);

				if (listTypesNode.getLength() != 0) {
					designDAL.deleteAllDesign();
				}

				for (int i = 0; i < listTypesNode.getLength(); i++) {
					Element typeElement = (Element) listTypesNode.item(i);

					Design design = new Design();

					String nameDesign = parser.getValue(typeElement, KEY_NAME);
					String colorTitleItem = parser.getValue(typeElement,
							KEY_TITLE_ITEM);
					String colorDescItem = parser.getValue(typeElement,
							KEY_DESC_ITEM);
					String colorBack = parser.getValue(typeElement,
							KEY_BACK_ITEM);
					int idDesign = Integer.parseInt(parser.getValue(
							typeElement, KEY_ID));

					String imageURL = URL_IMAGE + "/" + idDesign;

					design.setName(nameDesign);
					design.setId(idDesign);
					design.setColorDescriptionItem(colorDescItem);
					design.setColorTitleItem(colorTitleItem);
					design.setColorBackground(colorBack);
					design.setLogoApplication((TourAppGlobal
							.getImageFromUrl(imageURL)));

					designDAL.addDesign(design);
				}
			}
		} catch (Exception e) {
			System.out.println("Problem while parsing or saving XML : "
					+ e.getMessage());
		}
	}
}
