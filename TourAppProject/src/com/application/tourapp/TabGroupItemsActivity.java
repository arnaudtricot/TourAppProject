package com.application.tourapp;

import android.content.Intent;
import android.os.Bundle;

/**
 * L'activite qui regroupe les autres activites
 * 
 * @author Islam
 * 
 */

public class TabGroupItemsActivity extends ItemsGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("TypesActivity", new Intent(this,
				TypesActivity.class));
	}
}
