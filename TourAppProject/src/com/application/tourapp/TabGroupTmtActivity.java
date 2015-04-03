package com.application.tourapp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Regroupage pour le module TakeMeTour
 * 
 * @author Islam
 *
 */

public class TabGroupTmtActivity extends TmtsGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("TmtActivity", new Intent(this,
				TmtActivity.class));
	}
}
