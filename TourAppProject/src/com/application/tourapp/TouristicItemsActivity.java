package com.application.tourapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.application.tourapp.async.TourItemsAsyncActivity;
import com.application.tourapp.baseadapter.ItemListBaseAdapter;
import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.model.City;
import com.application.tourapp.model.Item;

/**
 * Les item touristique
 * 
 * @author Islam
 *
 */

public class TouristicItemsActivity extends GenericActivity {
	
	public ListView listItems; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_activity);
        setHeader(getResources().getString(R.string.MenuOne), true, true);
        Intent intent = getIntent();
        Context context = getApplicationContext();
        
        String typeId = intent.getStringExtra("id_type");
        String cityId = Integer.toString(getSeletedCity().getId());
        //new TourItemsListWebService().fillTourItemsByType(typeId, context);
        new TourItemsAsyncActivity(this, typeId, cityId).execute();
        ArrayList<Item> ItemDetails = GetSearchResults(typeId, cityId, context);
        
        listItems = (ListView) findViewById(R.id.listV_main);
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
                ItemsGroupActivity parentActivity = (ItemsGroupActivity)getParent();
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
         		parentActivity.startChildActivity("ItemDetailsActivity", itemDetailsScreenIntent);
                //Sending data to another Activity
                /*itemsScreen.putExtra("name", "alo");
                Log.e("n", "ALO");*/
                
        	}  
        });
    }
    
    public ArrayList<Item> GetSearchResults(String type, String cityId, Context context) {
    	return new TourItemDAL(context).getAllTourItemsByType(type, cityId);
    }
    
    public City getSeletedCity() {
    	List<City> listCities = new CityDAL(getApplicationContext()).getAllCities();
    	City selectedCity = null;
    	for(City city : listCities) {
    		if(city.isSelected()) {
    			selectedCity = city;
    			break;
    		}
    	}
    	return selectedCity;
    }
}