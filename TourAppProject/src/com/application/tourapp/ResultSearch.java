package com.application.tourapp;

import java.util.ArrayList;

import com.application.tourapp.baseadapter.ItemListBaseAdapter;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;

import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.Type;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Ludovic
 *
 * Résultat de la recherche sur les items
 * 
 */
public class ResultSearch extends GenericActivity {



	public ListView listItems; 
	ArrayList<Item> ItemDetails = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_search);
		LinearLayout layout = (LinearLayout) findViewById(R.id.resultSearch);
		Design design = new DesignDAL(getApplicationContext())
		.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));
		setHeader(getResources().getString(R.string.item), true, true);
		Intent intent = getIntent();




		String typeId = intent.getStringExtra("id_type");
		String cityId = intent.getStringExtra("id_ville");
		String motClef = intent.getStringExtra("id_motclef");

		if (cityId != null){
			ItemDetails = toGetSearchResults(typeId, cityId, getApplicationContext(),motClef);
		}else{
			ItemDetails = toGetSearchResultsAll(typeId, getApplicationContext(),motClef);
		}



		listItems = (ListView) findViewById(R.id.detailiTem);
		listItems.setAdapter(new ItemListBaseAdapter(this, ItemDetails));





		listItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) { 





				Object o = listItems.getItemAtPosition(position);
				Item objItem = (Item)o;
				System.out.println("The Item name = " + objItem.getName());
				//Toast.makeText(ItemsActivity.this, "You have chosen : " + " " + obj_Item.getName(), Toast.LENGTH_LONG).show();
				//Starting a new Intent
				Intent itemDetailsScreenIntent = new Intent(getApplicationContext(), ItemDetailsActivity.class);

				/*
				 * EGA - la transmition des valeur necessair vers la page des detailles 
				 * de chaqueedees4 item touristic
				 */
				itemDetailsScreenIntent.putExtra("name_item", objItem.getName());
				itemDetailsScreenIntent.putExtra("item_description", objItem.getDescription());
				itemDetailsScreenIntent.putExtra("item_site", objItem.getWebsite());
				//itemDetailsScreenIntent.putExtra("item_number", objItem.getNumber());
				itemDetailsScreenIntent.putExtra("item_lng", objItem.getLongitude());
				itemDetailsScreenIntent.putExtra("item_lat", objItem.getLatitude());
				itemDetailsScreenIntent.putExtra("item_number", objItem.getEmail());
				itemDetailsScreenIntent.putExtra("item_id", Integer.toString(objItem.getId()));
				itemDetailsScreenIntent.putExtra("item_object", objItem);


				startActivity(itemDetailsScreenIntent);
				//Sending data to another Activity
				/*itemsScreen.putExtra("name", "alo");
	                Log.e("n", "ALO");*/

			}  
		});
	}

	public ArrayList<Type> GetSearchResults(Context context) {
		return new TypeDAL(context).getAlltypes();
	}

	// Recherche dans la base si un item correspond à la recherche. traitement pour une ville selectionnéé

	public ArrayList<Item> toGetSearchResults(String type, String cityId, Context context, String motClef) {
		// variables locales
		ArrayList<Item> resultatBase = null;
		ArrayList<Item> resultatGenerale = null;

		resultatGenerale = new ArrayList<Item>();

		resultatBase =new TourItemDAL(context).getAllTourItemsByType(type, cityId);

		// Traitement de la recher sur le nom du item ou de la description
		if (resultatBase != null){
			if (motClef != " " && resultatBase != null) {
				for (Item item : resultatBase){

					if (item.getName().toLowerCase().contains(motClef.toLowerCase()) || item.getDescription().toLowerCase().contains(motClef.toLowerCase())){
						resultatGenerale.add(item);
					}
				}// fin for
			}else{
				resultatGenerale.addAll(resultatBase);
			}
		}// fin if*/
		return resultatGenerale;
	}



	// Recherche dans la base si un item correspond à la recherche. traitement quelquesoit la ville

	public ArrayList<Item> toGetSearchResultsAll(String type, Context context, String motClef) {
		// variables locales
		ArrayList<Item> resultatBase = null;
		ArrayList<Item> resultatGenerale = null;

		resultatGenerale = new ArrayList<Item>();

		resultatBase = new TourItemDAL(context).getAllTourItemsByTypeOnly(type);
		// Traitement de la recher sur le nom du item ou de la description
		if (resultatBase != null){
			if (motClef != " " && resultatBase != null) {
				for (Item item : resultatBase){

					if (item.getName().toLowerCase().contains(motClef.toLowerCase()) || item.getDescription().toLowerCase().contains(motClef.toLowerCase())){
						resultatGenerale.add(item);

					}
				}// fin for
			}else{
				resultatGenerale.addAll(resultatBase);
			}
		}// fin if*/
		return resultatGenerale;
	}

}