package com.application.tourapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.FavoritesDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;

/**
 * La classe qui resprente les details d'une activite
 * 
 * @author Ramy
 *
 */

public class ItemDetailsActivity extends GenericActivity {
	private Spinner spinner;
	private Button buttonActive;
	
	List<String> list = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.item_details);
		
		//for fix error android.view.windowManager$BadTokenException
		View viewToLoad = LayoutInflater.from(this).inflate(R.layout.item_details, null);
		this.setContentView(viewToLoad); 
		
		setHeader(getResources().getString(R.string.Det), true, true);
		Intent intent = getIntent();
		final String itemName = intent.getStringExtra("name_item");
		final String itemDescription = intent.getStringExtra("item_description");
		final String itemPhoneNumber = intent.getStringExtra("item_number");
		final String itemWebsite = intent.getStringExtra("item_site");
		final String itemLng = intent.getStringExtra("item_lng");
		final String itemLat = intent.getStringExtra("item_lat");
		final String itemId = intent.getStringExtra("item_id");

		TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
		TextView itemDescriptionView = (TextView) findViewById(R.id.itemDescription);
		Button buttonSite = (Button) findViewById(R.id.buttonSite);
		Button buttonAppler = (Button) findViewById(R.id.buttonAppler);
		Button buttonMap = (Button) findViewById(R.id.buttonMap);
		final Button buttonFav = (Button) findViewById(R.id.buttonFav);
		ImageView imageItem = (ImageView) findViewById(R.id.imageItem);
		itemTitleView.setText(itemName);
		itemDescriptionView.setText(itemDescription);
		Design design = new DesignDAL(getApplicationContext())
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		itemTitleView.setTextColor(Color.parseColor("#"
				+ design.getColorTitleItem()));
		itemDescriptionView.setTextColor(Color.parseColor("#"
				+ design.getColorDescriptionItem()));
		LinearLayout layout = (LinearLayout) findViewById(R.id.itemDetail);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));		
		Item itemObject = (Item) getIntent()
				.getSerializableExtra("item_object");
		if (new FavoritesDAL(getApplicationContext()).existsFavorit(itemObject)) {
			buttonFav.setBackgroundColor(Color.YELLOW);
		}
		if (itemObject.getImageOne() == null) {
			imageItem.setImageResource(R.drawable.unkown);
		} else {
			byte[] blob = itemObject.getImageOne();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			if (bmp != null) {
				imageItem.setImageBitmap(bmp);
			} else {
				imageItem.setImageResource(R.drawable.unkown);
			}
		}

		buttonSite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = itemWebsite;
				if (!url.startsWith("http://") || !url.startsWith("https://")) {
					url = "http://" + url;
				}
				Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browse);
			}
		});

		buttonAppler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + itemPhoneNumber));
				startActivity(callIntent);
			}
		});
		// Ramy
	/*	buttonMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent itemMapScreenIntent = new Intent(
						getApplicationContext(), ItemMapActivity.class);
				ItemsGroupActivity parentActivity = (ItemsGroupActivity) getParent();
				itemMapScreenIntent.putExtra("lng_item", itemLng);
				itemMapScreenIntent.putExtra("lat_item", itemLat);
				parentActivity.startChildActivity("ItemMapActivity",
						itemMapScreenIntent);

			}
		});*/
		
		//Mouhamadou
		buttonMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent intentMaps = new Intent(ItemDetailsActivity.this, ItemMapActivity.class);
				
				intentMaps.putExtra("lng_item", itemLng);
				intentMaps.putExtra("lat_item", itemLat);
				intentMaps.putExtra("name_item", itemName);
				intentMaps.putExtra("id_item", itemId);
				//Démarrer une activité de façon explicite en l'occurence la page suivante
				startActivity(intentMaps);
			}
		}); 

		buttonFav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Item item = new TourItemDAL(getApplicationContext())
						.getTourItem(Integer.parseInt(itemId));
				new FavoritesDAL(getApplicationContext()).addFavorit(item);
				if (new FavoritesDAL(getApplicationContext())
						.existsFavorit(item)) {
					buttonFav.setBackgroundColor(Color.YELLOW);
				} else {
					buttonFav.setBackgroundColor(Color.parseColor("#eeffffff"));
				}
			}
		});

		imageItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent itemImageGalleryScreenIntent = new Intent(
						getApplicationContext(), GalleryView.class);
				Item itemTemp = (Item) getIntent().getSerializableExtra(
						"item_object");
				Item item = new TourItemDAL(getApplicationContext())
						.getTourItem(itemTemp.getId());
				if (item.getImageOne() != null && item.getImageTwo() != null
						&& item.getImageThree() != null) {
					item.setListImages(new ArrayList<byte[]>());
					item.getListImages().add(item.getImageOne());
					item.getListImages().add(item.getImageTwo());
					item.getListImages().add(item.getImageThree());
					itemImageGalleryScreenIntent.putExtra("item_object", item);
					startActivity(itemImageGalleryScreenIntent);
				} else {
					System.out.println("Pas des images");
				}
			}
		});
		
		
		/**
		 * fonction du menu déroulant dans la page de description d'un sous- theme
		 * 
		 * 
		 * @param
		 */
		spinner = (Spinner) findViewById(R.id.spin);
	
        list.add("Informations générales");
        list.add("Equipements pratiques");
        
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		
		/*ArrayAdapter<String> adapter = ArrayAdapter.createFromResource( this, R.array.medicine_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(adapter);*/
		
		// Spinner item selection Listener  
        addListenerOnSpinnerItemSelection();
        
     // Button click Listener 
        addListenerOnButton();
   
		
	}
	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		spinner = (Spinner) findViewById(R.id.spinner1);
        
		buttonActive = (Button) findViewById(R.id.activation);
 
		buttonActive.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
            	Toast.makeText(ItemDetailsActivity.this,"un clic sur le bouton : " + "\n" + String.valueOf(spinner.getSelectedItem()) ,Toast.LENGTH_LONG).show();
 
            }
 
        });
	}
    // Add spinner data
	private void addListenerOnSpinnerItemSelection() {
		// TODO Auto-generated method stub
		//spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
		
}

