package com.application.tourapp;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.application.tourapp.async.MessagesAsyncActivity;
import com.application.tourapp.baseadapter.MessageListBaseAdapter;
import com.application.tourapp.dal.MessageDAL;
import com.application.tourapp.model.Message;

/**
 * Cette classe resprsente la liste des messages 
 * 
 * @author Ramy
 *
 */

public class MessagesActivity extends GenericActivity {	

	public ListView listMessages;
	
	public void init() {
        new MessagesAsyncActivity(this).execute();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        setHeader(getResources().getString(R.string.Msg), true, true);
        Context context = getApplicationContext();
        new MessagesAsyncActivity(this).execute();
        ArrayList<Message> messagesDetails = GetSearchResults(context);
        System.out.println("The messages array size = " + messagesDetails.size());
        listMessages = (ListView) findViewById(R.id.listV_main);
        listMessages.setAdapter(new MessageListBaseAdapter(this, messagesDetails));
        listMessages.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
        		Object o = listMessages.getItemAtPosition(position);
            	Message objMessage = (Message) o;
        		Toast.makeText(MessagesActivity.this, getResources().getString(R.string.Message)+": " + " " + objMessage.getText(), Toast.LENGTH_LONG).show();                
        	}  
        });
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	init();
    }
    
    public ArrayList<Message> GetSearchResults(Context context) {
    	return new MessageDAL(context).getAllMessages();
    }
}
