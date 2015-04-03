package com.application.tourapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.httppost.FeedBackHttpPost;
import com.application.tourapp.model.City;
import com.application.tourapp.model.FeedBack;

/**
 * Un ecran pour le paramtrage de l'application
 * 
 * @author Islam
 *
 */

public class SettingsActivity extends GenericActivity {
	CityDAL cityDAL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button mButton, btnValider;
		final Spinner spinner;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		setHeader(getResources().getString(R.string.Settings), true, true);

		cityDAL = new CityDAL(getApplicationContext());

		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<City> dataAdapter = new ArrayAdapter<City>(this,
				android.R.layout.simple_spinner_item, cityDAL.getAllCities());
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		btnValider = (Button) findViewById(R.id.btnValider);
		btnValider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				for (City city : cityDAL.getAllCities()) {
					city.setSelected(false);
					cityDAL.addCity(city);
				}
				City selectedCity = (City) spinner.getSelectedItem();
				selectedCity.setSelected(true);
				cityDAL.addCity(selectedCity);
			}
		});

		mButton = (Button) findViewById(R.id.button3);
		final EditText mEdit = (EditText) findViewById(R.id.editText1);

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Log.v("EditText", mEdit.getText().toString());
				FeedBack feedBack = new FeedBack();
				feedBack.setMessage(mEdit.getText().toString());
				if (!feedBack.getMessage().trim().equals("")) {
					if (new FeedBackHttpPost().postFeedback(feedBack)) {
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.Sent),
								Toast.LENGTH_SHORT).show();
						mEdit.setText("");
					} else {
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.NotSent),
								Toast.LENGTH_SHORT).show();
						mEdit.setText("");
					}
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.Data),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}
}
