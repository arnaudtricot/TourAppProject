package com.application.tourapp;

import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class StreetViewActivity extends FragmentActivity {
	//Google Maps
		private GoogleMap StreetViewMap;
		
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			final String itemLng = getIntent().getStringExtra("item_long");
			final String itemLat = getIntent().getStringExtra("item_lat");
			final String itemName = getIntent().getStringExtra("item_name");
			
			//le fichier map_item_activity sera la vue courante de cet écran
			setContentView(R.layout.streetview_activity);
		
			try {
	            // Loading map
	            initialiserCarte();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			//ajouter un marqueur sur la carte
			StreetViewMap.addMarker(placerUnMarqueurSurCarte(itemLng, itemLat, itemName));
			//Bouger la caméra vers le lieu de localisation avec de l'animation
			StreetViewMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placerUnMarqueurSurCarte(itemLng, itemLat, itemName).getPosition(),17));
			
		}	
			//fonction pour charger une carte Google Maps en Hybrid
			public void initialiserCarte(){
				 if (StreetViewMap == null) {
					 StreetViewMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.streetmap)).getMap();
					 
					 StreetViewMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			        // check if map is created successfully or not
			        if (StreetViewMap == null) {
			        	//System.out.println("Google Maps ne s'est pas chargé!!!");
			        	 Toast.makeText(this, "Google Maps ne s'est pas chargé!!!", Toast.LENGTH_LONG).show();
			        }
				 }
			}
			//fonction pour placer un marqueur sur la carte
			public MarkerOptions placerUnMarqueurSurCarte(String itemLng, String itemLat, String itemName){
				
				double latitude= Double.parseDouble(itemLat);
				double longitude=Double.parseDouble(itemLng);
				// create marker
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(itemName);
				// Changer la couleur de l'icone du marqueur en vert
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				
				
				return marker;
			}
			//pas utiliser pour le moment
			public void startIntentToGoogleMap(LatLng latlng, String labelTo)
		    {
		  
		         String uri = String.format(Locale.FRENCH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", latlng.latitude, latlng.longitude, labelTo);
		  
		            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		            try
		            {
		                startActivity(intent);
		            }
		            catch(ActivityNotFoundException ex)
		            {
		                try
		                {
		                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		                    startActivity(unrestrictedIntent);
		                }
		                catch(ActivityNotFoundException innerEx)
		                {
		                    Toast.makeText(this, "Stp installe une application Maps pour que ça marche", Toast.LENGTH_LONG).show();
		                }
		            }
		    }
		  
				//pas utiliser pour le moment
		    public void startIntentToGoogleStreeView(LatLng latlng, String labelTo)
		    {
		  
		  
		            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("google.streetview:cbll="+ latlng.latitude+","+latlng.longitude+"&cbp=1,99.56,,1,1&mz=21"));
		  
		  
		  
		            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		            try
		            {
		                startActivity(intent);
		            }
		            catch(ActivityNotFoundException ex)
		            {
		                try
		                {
		                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", latlng.latitude, latlng.longitude, labelTo);
		                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		                    startActivity(unrestrictedIntent);
		                }
		                catch(ActivityNotFoundException innerEx)
		                {
		                    Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
		                }
		            }
		    }
}
