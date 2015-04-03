package com.application.tourapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

public class NavigationActivity extends FragmentActivity{
	//Google Maps
		private GoogleMap NavigationMap;
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			final String itemLng = getIntent().getStringExtra("lng_item");
			final String itemLat = getIntent().getStringExtra("lat_item");
			
			//le fichier map_item_activity sera la vue courante de cet écran
			setContentView(R.layout.navigation_activity);
		
			try {
	            // Loading map
	            initialiserCarte();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			//ajouter un marqueur sur la carte
			NavigationMap.addMarker(placerUnMarqueurSurCarte(itemLng, itemLat));
			//Bouger la caméra vers le lieu de localisation avec de l'animation
			NavigationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placerUnMarqueurSurCarte(itemLng, itemLat).getPosition(),10));
			//instanciation de l'objet btnvp dans xml
		}
			
			
			//fonction pour charger une carte Google Maps
			public void initialiserCarte(){
				 if (NavigationMap == null) {
					 NavigationMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();;
			        // check if map is created successfully or not
			        if (NavigationMap == null) {
			        	System.out.println("Google Maps ne s'est pas chargé!!!");
			        }
				 }
			}
			//fonction pour placer un marqueur sur la carte
			public MarkerOptions placerUnMarqueurSurCarte(String itemLng, String itemLat){
				
				double latitude= Double.parseDouble(itemLat);
				double longitude=Double.parseDouble(itemLng);
				String titre = "Egypte";
				// create marker
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(titre);
				// Changer la couleur de l'icone du marqueur en vert
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				
				
				return marker;
			}
}
