package com.application.tourapp.webservice;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.application.tourapp.dal.MessageDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Message;
import com.application.tourapp.xml.XMLParser;

public class MessagesListWebService {
	static final String URL = TourAppGlobal.HOST_NAME+"/"+TourAppGlobal.WEB_SERVICE+"/"+TourAppGlobal.WEB_SERVICE_MESSAGES;
	static final String KEY_MESSAGE = "models.Message";
	static final String KEY_SUBJECT = "subject";
	static final String KEY_TEXT = "text";
	static final String KEY_ID = "id";
	
	public ArrayList<Message> listMessage = new ArrayList<Message>();
	
	/**
	 * Permet de faire appel au web service de l'application web afin de
	 * récupérer les données sur les messages affichés
	 * 
	 */
	public ArrayList<Message> fillMessages(Context context) {
		try{
			listMessage.clear();
			XMLParser parser = new XMLParser();
			String urlWebServices = URL;
			MessageDAL messageDAL = new MessageDAL(context);
			//String filePath = TourAppGlobal.ITEMS_LOCAL_FOLDER+TourAppGlobal.ITEMS_LOCAL_NAME+"_";
			boolean connected = TourAppGlobal.isOnline(context);
			if(connected && TourAppGlobal.IS_CONNECT) { 			
				String xmlToSave = parser.getXmlFromUrl(urlWebServices);
				//TourAppGlobal.writeFile(context, xmlToSave, filePath);
				//String xml = TourAppGlobal.readFile(context, filePath);
				Document doc = parser.getDomElement(xmlToSave);
				NodeList itemsNodeList = doc.getElementsByTagName(KEY_MESSAGE);
				messageDAL.deleteAllMessages();
				for (int i = 0; i < itemsNodeList.getLength(); i++) {
					Element itemElement = (Element) itemsNodeList.item(i);
					Message message = new Message();
					message.setId(Integer.parseInt(parser.getValue(itemElement, KEY_ID)));
					message.setSubject(parser.getValue(itemElement, KEY_SUBJECT));
					message.setText(parser.getValue(itemElement, KEY_TEXT));
					//tourisItem.setImageNumber(1);
					//listItems.add(tourisItem);
					messageDAL.addMessage(message);
				}
			}
		} catch(Exception e) {
			System.out.println("Exception here ... "+e.getMessage());
		}
		return listMessage;
	}
}
