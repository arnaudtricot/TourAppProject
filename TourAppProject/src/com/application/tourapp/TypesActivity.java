package com.application.tourapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.application.tourapp.async.TypesAsyncActivity;
import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.City;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.Type;

/**
 * Cette activite est pour les types
 * 
 * @author Ramy
 * 
 */

public class TypesActivity extends GenericActivity {
	public Button monBut;
	private Type type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.types_activity);
		setHeader(getResources().getString(R.string.Types), true, true);
		Context context = getApplicationContext();
		new TypesAsyncActivity(this).execute();
		int countType = GetCountResults(context);
		type = GetSearchResults(context, TourAppGlobal.INDICE_TYPE);

		/* traitement pour le bouton */
		monBut = (Button) findViewById(R.id.buttonRechercher);

		monBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(TypesActivity.this,
						SearchActivity.class);
				startActivity(intent);
			}
		});
		TextView typeView = (TextView) findViewById(R.id.typeText);
		typeView.setText(type.getName());

		ImageButton imageType = (ImageButton) findViewById(R.id.imageType);
		if (type.getImageBlob() == null) {
			imageType.setImageResource(R.drawable.unkown);
		} else {
			byte[] blob = type.getImageBlob();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			if (bmp != null) {
				imageType.setImageBitmap(bmp);
			} else {
				imageType.setImageResource(R.drawable.unkown);
			}
		}
		imageType.setContentDescription(type.getName());
		imageType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Item> listItem = new TourItemDAL(v.getContext())
						.getAllTourItemsByType(String.valueOf(type.getId()),
								String.valueOf(getSeletedCity().getId()));
				if (listItem.size() == 0) {
					message();
				} else {
					Type objType = type;
					System.out.println("The type name = " + objType.getName());
					Intent touristicItemsScreenIntent = new Intent(
							getApplicationContext(),
							TouristicItemsActivity.class);
					ItemsGroupActivity parentActivity = (ItemsGroupActivity) getParent();
					touristicItemsScreenIntent.putExtra("id_type",
							Integer.toString(objType.getId()));
					parentActivity.startChildActivity("TouristicItemsActivity",
							touristicItemsScreenIntent);
				}
			}
		});

		TextView text = (TextView) findViewById(R.id.nbTypes);
		String numItem = String.valueOf(TourAppGlobal.INDICE_TYPE + 1);
		text.setText(numItem + "/" + countType);
		ImageButton imageGauche = (ImageButton) findViewById(R.id.typeGauche);
		ImageButton imageDroite = (ImageButton) findViewById(R.id.typeDroite);
		if (TourAppGlobal.INDICE_TYPE == 0) {
			imageGauche.setEnabled(false);
		} else {
			imageGauche.setEnabled(true);
		}
		if (TourAppGlobal.INDICE_TYPE == countType - 1) {
			imageDroite.setEnabled(false);
		} else {
			imageDroite.setEnabled(true);
		}
	}

	public int GetCountResults(Context context) {
		return new TypeDAL(context).getCountTypes();
	}

	public Type GetSearchResults(Context context, int idType) {
		return new TypeDAL(context).getTypeByNumRow(idType);
	}

	public City getSeletedCity() {
		List<City> listCities = new CityDAL(getApplicationContext())
				.getAllCities();
		City selectedCity = null;
		for (City city : listCities) {
			if (city.isSelected()) {
				selectedCity = city;
				break;
			}
		}
		return selectedCity;
	}

	public void message() {
		Toast.makeText(getApplicationContext(), "Ce type d'activité ne possède pas d'items touristiques !",
                Toast.LENGTH_SHORT).show();
	}
}