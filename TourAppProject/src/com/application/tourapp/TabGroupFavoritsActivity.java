package com.application.tourapp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Le module des favoris
 * 
 * @author Islam
 *
 */

public class TabGroupFavoritsActivity extends FavoritesGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("FavoritesActivity", new Intent(this,
				FavoritesActivity.class));
	}
}
