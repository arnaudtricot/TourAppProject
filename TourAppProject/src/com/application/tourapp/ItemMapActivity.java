package com.application.tourapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/*;
 import android.support.v4.app.FragmentManager;*/

/*import com.google.android.gms.maps.CameraUpdateFactory;


 import com.google.android.gms.maps.model.LatLng;
 import com.google.android.gms.maps.model.Marker;
 import com.google.android.gms.maps.model.MarkerOptions;*/

/**
 * Cette classe représente l'écran des cartes GoogleMaps API V2
 * 
 * @author Mouhamadou
 * 
 */

public class ItemMapActivity extends FragmentActivity {

	// Google Maps
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String itemLng = getIntent().getStringExtra("lng_item");
		final String itemLat = getIntent().getStringExtra("lat_item");
		final String itemName = getIntent().getStringExtra("name_item");
		final String itemId = getIntent().getStringExtra("id_item");

		// le fichier map_item_activity sera la vue courante de cet écran
		setContentView(R.layout.map_item_activity);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.mapItemLayout);
		Design design = new DesignDAL(getApplicationContext())
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));
		try {
			// Loading map
			initialiserCarte();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ajouter un marqueur sur la carte
		googleMap.addMarker(placerUnMarqueurSurCarte(itemLng, itemLat, itemName));
		// Bouger la caméra vers le lieu de localisation avec de l'animation
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				placerUnMarqueurSurCarte(itemLng, itemLat, itemName).getPosition(), 10));

		// instanciation de l'objet btnvp dans xml pour les photos
		Button BtCarte = (Button) findViewById(R.id.btncarte);
		BtCarte.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// on crée l'intent qui nous permet d'afficher l'autre activity
				Intent intent = new Intent(ItemMapActivity.this,
						AndroidGridLayoutActivity.class);
				intent.putExtra("id_item", itemId);
				// on le lie à l'autre activité par le biais du clic sur le
				// bouton btnvp
				startActivity(intent);
			}
		});

		// instanciation de l'objet btnvp dans xml
		Button BtRetour = (Button) findViewById(R.id.btnback);
		BtRetour.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// ferme l'activité courante et dépile l'activité en dessus
				ItemMapActivity.this.finish();
			}
		});

		// instanciation du bouton Wikipedia dans xml
		Button ButtonWiki = (Button) findViewById(R.id.btWiki);
		ButtonWiki.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// à connecter avec la base de données
				Uri uriwiki = allerSurWikipedia("Pyramides_d'Égypte");
				Intent wikiintent = new Intent(Intent.ACTION_VIEW, uriwiki);
				startActivity(wikiintent);
			}
		});

		Button ButtonStreetView = (Button) findViewById(R.id.btStrvw);
		ButtonStreetView.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent streetViewintent = new Intent(ItemMapActivity.this,
						StreetViewActivity.class);
				streetViewintent.putExtra("item_lat", itemLat);
				streetViewintent.putExtra("item_long", itemLng);
				streetViewintent.putExtra("item_name", itemName);
				// on le lie à l'autre activité par le biais du clic sur le
				// bouton btnvp
				startActivity(streetViewintent);
			}
		});

		Button ButtonNavigation = (Button) findViewById(R.id.btNav);
		ButtonNavigation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// saddr = latitude and longitude of starting point.
				// daddr = latitude and longitude of destination point.
				Intent navigation = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?saddr=47.28,-1.52&daddr="+itemLat+","+itemLng));
				// Intent navigation = new Intent(Intent.ACTION_VIEW,
				// Uri.parse("google.navigation:q=New+York+NY"));
				startActivity(navigation);
			}
		});

	}

	// fonction pour charger une carte Google Maps
	public void initialiserCarte() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			;
			// check if map is created successfully or not
			if (googleMap == null) {
				System.out.println("Google Maps ne s'est pas chargé!!!");
			}
		}
	}

	// méthode pour démarrer un navigateur internet pour wikipedia
	// retourne un uri
	protected Uri allerSurWikipedia(String sitewikipedia) {
		// sitewikipedia = "Pyramides_d'Égypte";
		Uri uri = Uri.parse("http://fr.wikipedia.org/wiki/" + sitewikipedia);
		System.out.println(uri);
		return uri;
	}

	// fonction pour placer un marqueur sur la carte
	public MarkerOptions placerUnMarqueurSurCarte(String itemLng, String itemLat, String itemName) {

		double latitude = Double.parseDouble(itemLat);
		double longitude = Double.parseDouble(itemLng);
		// create marker
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latitude, longitude)).title(itemName);
		// Changer la couleur de l'icone du marqueur en vert
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		return marker;
	}

}
