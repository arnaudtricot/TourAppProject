package com.application.tourapp;

import java.util.ArrayList;
import java.util.List;

import com.application.tourapp.baseadapter.ImageAdapter;
import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Affichage de la liste d'images d'un item touristique
 * 
 * @author Ludovic
 *
 */
public class AndroidGridLayoutActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout_activity);
        getIntent();
        GridView gridView = (GridView) findViewById(R.id.grid_view);
		Design design = new DesignDAL(getApplicationContext())
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		gridView.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));
        final String itemId = getIntent().getStringExtra("id_item");
        
        Item item = new TourItemDAL(getApplicationContext()).getTourItem(Integer.valueOf(itemId));
 
        List<byte[]> listImage = new ArrayList<byte[]>();
        listImage.add(item.getImageOne());
        listImage.add(item.getImageTwo());
        listImage.add(item.getImageThree());
        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this, listImage));
        
        /**
         * On Click event for Single Gridview Item
         * */
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra("id_item", itemId);
                startActivity(i);
            }
        });
    }
}
