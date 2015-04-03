package com.application.tourapp;

import android.content.Intent;
import android.os.Bundle;

/**
 * La classe qui represente les messages en les groupant
 * 
 * @author Ramy
 *
 */

public class TabGroupMessagesActivity extends ItemsGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("MessagesActivity", new Intent(this,
				MessagesActivity.class));
	}
}
