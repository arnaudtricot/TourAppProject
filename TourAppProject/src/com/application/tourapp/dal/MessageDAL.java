package com.application.tourapp.dal;

import java.util.ArrayList;

import android.content.Context;

import com.application.tourapp.databasehandler.MessageDatabaseHandler;
import com.application.tourapp.model.Message;

public class MessageDAL {
	
	MessageDatabaseHandler messageDB;
	
	public MessageDAL(Context context) {
		messageDB = new MessageDatabaseHandler(context);
	}
	
	public void addMessage(Message message) {
		messageDB.addMessage(message);
	}
	
	public ArrayList<Message> getAllMessages() {
		return messageDB.getAllMessages();
	}
	
	public void deleteAllMessages() {
		messageDB.deleteAllMessges();
	}

}
