package com.application.tourapp;

import java.util.ArrayList;
import java.util.List;

import com.application.tourapp.baseadapter.ImageAdapter;
import com.application.tourapp.baseadapter.ImageSwipeAdapter;
import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.TourItemDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
 
/**
 * @author Ludovic
 *
 * Cette classe permet l'affichage de toutes les images d'un item
 */
public class FullImageActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_image_activity);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.swipe);
		Design design = new DesignDAL(getApplicationContext())
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));

        final String itemId = getIntent().getStringExtra("id_item");
 
        // get intent data
        /*  Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);*/
        Item item = new TourItemDAL(getApplicationContext()).getTourItem(Integer.valueOf(itemId));
        
        List<byte[]> listImage = new ArrayList<byte[]>();
        listImage.add(item.getImageOne());
        listImage.add(item.getImageTwo());
        listImage.add(item.getImageThree());
        
    	ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
    	ImageSwipeAdapter adapter = new ImageSwipeAdapter(this, listImage);
    	viewPager.setAdapter(adapter);
    }
 
}
