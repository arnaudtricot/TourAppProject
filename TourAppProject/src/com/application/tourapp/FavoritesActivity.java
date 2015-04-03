package com.application.tourapp;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.application.tourapp.baseadapter.ItemListBaseAdapter;
import com.application.tourapp.dal.FavoritesDAL;
import com.application.tourapp.model.Item;

/**
 * Cette classe represente l'activite des favoris
 * @author Ramy
 *
 */

public class FavoritesActivity extends GenericActivity {
	/** Called when the activity is first created. */

	public ListView listItems;

	/**
	 * Initialise la page des favoris
	 */
	public void init() {
		setContentView(R.layout.items_activity);
		setHeader(getResources().getString(R.string.Fav), true, true);
		Context context = getApplicationContext();

		ArrayList<Item> ItemDetails = GetSearchResults(context);

		listItems = (ListView) findViewById(R.id.listV_main);
		listItems.setAdapter(new ItemListBaseAdapter(this, ItemDetails));
		listItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = listItems.getItemAtPosition(position);
				Item objItem = (Item) o;
				System.out.println("The Item name = " + objItem.getName());
				Intent itemDetailsScreenIntent = new Intent(
						getApplicationContext(), ItemDetailsActivity.class);
				FavoritesGroupActivity parentActivity = (FavoritesGroupActivity) getParent();
				/*
				 * EGA - la transmition des valeur necessair vers la page des
				 * detailles de chaqueedees4 item touristic
				 */
				itemDetailsScreenIntent.putExtra("name_item", objItem.getName());
				itemDetailsScreenIntent.putExtra("item_description",
						objItem.getDescription());
				itemDetailsScreenIntent.putExtra("item_site",
						objItem.getWebsite());
				// itemDetailsScreenIntent.putExtra("item_number",
				// objItem.getNumber());
				itemDetailsScreenIntent.putExtra("item_lng",
						objItem.getLongitude());
				itemDetailsScreenIntent.putExtra("item_lat",
						objItem.getLatitude());
				itemDetailsScreenIntent.putExtra("item_number",
						objItem.getEmail());
				itemDetailsScreenIntent.putExtra("item_id",
						Integer.toString(objItem.getId()));
				itemDetailsScreenIntent.putExtra("item_object", objItem);
				parentActivity.startChildActivity("ItemDetailsActivity",
						itemDetailsScreenIntent);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_activity);
		setHeader("Favorits", true, true);
		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}

	public ArrayList<Item> GetSearchResults(Context context) {
		return (ArrayList<Item>) new FavoritesDAL(context).getAllFavorites()
				.getListFavorits();
	}
}