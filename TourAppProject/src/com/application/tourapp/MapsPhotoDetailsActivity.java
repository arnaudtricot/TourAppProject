package com.application.tourapp;

import android.graphics.Color;import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

/**
 * Cette classe gère l'activité qui affiche les l
 * @author Modou
 *
 */
public class MapsPhotoDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_details_view);
		LinearLayout layout = (LinearLayout) findViewById(R.id.googleDetail);
		Design design = new DesignDAL(getApplicationContext())
		.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));
		// Show the Up button in the action bar.
		//setupActionBar();
		
		//recevoir l'intent
		//récupérer l'intent qui démarre notre activity en appelant getIntent()
		getIntent();
		
		//instanciation du bouton Wikipedia dans xml
		Button ButtonWiki = (Button)findViewById(R.id.btnWiki);
		ButtonWiki.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				//à connecter avec la base de données	
				Uri uriwiki = allerSurWikipedia("Pyramides_d'Égypte");
				Intent wikiintent = new Intent(Intent.ACTION_VIEW, uriwiki);
				startActivity(wikiintent);
			}
		});
		
		Button ButtonOK = (Button)findViewById(R.id.btnOk);
		ButtonOK.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				//ferme l'activité (la page) courante et dépile l'activité (ItemMapActivity.java) en dessus
				MapsPhotoDetailsActivity.this.finish();
			}
		});
		
		Button ButtonStreetView = (Button)findViewById(R.id.btnStrvw);
		ButtonStreetView.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				Intent StreetViewintent = new Intent(MapsPhotoDetailsActivity.this, StreetViewActivity.class);
				//on le lie à l'autre activité par le biais du clic sur le bouton btnvp
				startActivity(StreetViewintent);
			}
		});
		
		Button ButtonNavigation = (Button)findViewById(R.id.btnNav);
		ButtonNavigation.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View arg0) {
				// saddr = latitude and longitude of starting point.
				//daddr = latitude and longitude of destination point.
				Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=42.35892,-71.05781&daddr=40.756054,-73.986951"));
				//Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=New+York+NY"));
				startActivity(navigation);
			}
		});
	}
	
	//méthode pour démarrer un navigateur internet pour wikipedia
	//retourne un uri
	protected Uri allerSurWikipedia(String sitewikipedia){
		//sitewikipedia = "Pyramides_d'Égypte";
		Uri uri = Uri.parse("http://fr.wikipedia.org/wiki/"+sitewikipedia);
		System.out.println(uri);
		return uri;
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
