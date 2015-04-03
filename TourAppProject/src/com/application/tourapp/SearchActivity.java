//
package com.application.tourapp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.City;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.Type;

/**
 * Activité gerant la recherche dans la base de données
 * 
 * @author ibrahima
 *
 */
public class SearchActivity extends GenericActivity {

	CityDAL cityDAL;
	TypeDAL TypeDAL;
	ListView listItems;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button recherche = null;
		final Spinner spinnerTheme;
		final Spinner spinnerVille;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchview_activity);
		LinearLayout layout = (LinearLayout) findViewById(R.id.searchView);
		Design design = new DesignDAL(getApplicationContext())
		.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));

		//Button mButton, btnValider;
		setHeader(getResources().getString(R.string.Search), true, true);

		
		// initialisation de la liste des thèmes

		TypeDAL = new TypeDAL(getApplicationContext());

		spinnerTheme = (Spinner) findViewById(R.id.listchoixtype);

		ArrayAdapter<Type> dataAdapter = new ArrayAdapter<Type>(this,
				android.R.layout.simple_spinner_item, TypeDAL.getAlltypes());
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTheme.setAdapter(dataAdapter);



		// initialisation de la liste des villes 

		cityDAL = new CityDAL(getApplicationContext());
		spinnerVille = (Spinner) findViewById(R.id.listchoixville);

		ArrayAdapter<City> dataAdapterVille = new ArrayAdapter<City>(this,
				android.R.layout.simple_spinner_item, cityDAL.getAllCities());
		dataAdapterVille
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		//creation de  la fonctionnalité toutes zones

		City tousZone = new City("toutes zones");

		dataAdapterVille.add(tousZone);

		spinnerVille.setAdapter(dataAdapterVille);




		recherche = (Button) findViewById(R.id.rechercher);

		//traitement lancé lorsque l'utilisateur clique sur le bouton lancer recherche

		recherche.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				ArrayList<Item> result = null;

				// reccuperation des infos saisies par l'utilisateur

				Type theme = (Type) spinnerTheme.getSelectedItem();
				City ville = (City) spinnerVille.getSelectedItem();

				String motClef = ((EditText) findViewById(R.id.precisionSearch)).getText().toString();

				if (ville.getName() != "toutes zones"){

					String typeId = Integer.toString(theme.getId());
					String cityId = Integer.toString(ville.getId());

					
					result = toGetSearchResults(typeId, cityId, getApplicationContext(),motClef);

					
					
					if (result.size() != 0 ){
				   
						
						Intent myIntent = new Intent(getApplicationContext(),ResultSearch.class);
						
					    
						myIntent.putExtra("id_type",
								Integer.toString(theme.getId()));
						myIntent.putExtra("id_ville",
								Integer.toString(ville.getId()));
						myIntent.putExtra("id_motclef",
								motClef);
						
						startActivity(myIntent);
					
					}else{
						
						message();
					}
				}else{

					String typeId = Integer.toString(theme.getId());
					result = toGetSearchResultsAll(typeId, getApplicationContext(),motClef);
					
				 if (result.size() != 0 ){
						   
					   Intent myIntent = new Intent(getApplicationContext(),ResultSearch.class);
						
					    
						myIntent.putExtra("id_type",
								Integer.toString(theme.getId()));
						
						
						myIntent.putExtra("id_motclef",
								motClef);
						
						startActivity(myIntent);
				
					}else{
						message();
					}
					
				
				}

				
/*
				Intent intent = new Intent(SearchActivity.this, ResultSearch.class);
				startActivity(intent);
*/





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


  public void message(){
	  AlertDialog alertDialog = new AlertDialog.Builder(
		        SearchActivity.this).create();
		 
		// Le titre
		
	
		// Le message
		alertDialog.setMessage("Aucun resultat trouvé !");
		 
		// L'icône

		 
		// Ajout du bouton "OK"
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		    public void onClick(final DialogInterface dialog, final int which) {
		        // Le code à exécuter après le clique sur le bouton
		        Toast.makeText(getApplicationContext(), "Modifiez votre recherche !",
		                Toast.LENGTH_SHORT).show();
		    }
		});
		 
		// Affichage
		alertDialog.show();
  }
}
