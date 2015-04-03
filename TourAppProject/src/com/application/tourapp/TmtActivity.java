package com.application.tourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.application.tourapp.async.TakeMeTourAsyncActivity;
import com.application.tourapp.dal.TakeMeTourDAL;
import com.application.tourapp.model.Item;
import com.application.tourapp.model.TakeMeTour;

/**
 * L'activite TMT
 * 
 * @author Islam
 *
 */

public class TmtActivity extends GenericActivity {
	
	public void loadDataTmt() {
		new TakeMeTourAsyncActivity(this).execute();
	}
	
	public void init() {
		System.out.println("MESSAGES ESSO");
		TextView textMorning = (TextView)findViewById(R.id.textMorning);
		TextView textAfter = (TextView)findViewById(R.id.textAfter);
		TextView textNight = (TextView)findViewById(R.id.textNight);
		ImageButton morningButton = (ImageButton)findViewById(R.id.morningButton);
		ImageButton afterButton = (ImageButton)findViewById(R.id.afterButton);
		ImageButton nightButton = (ImageButton)findViewById(R.id.nightButton);
		TakeMeTourDAL takeMeTourDAL = new TakeMeTourDAL(getApplicationContext());
		final TakeMeTour takeMeTour = takeMeTourDAL.getTakeMeTour();
		if(takeMeTour.getMorningItem() != null ||
		   takeMeTour.getNightItem() != null ||
		   takeMeTour.getAfternoonItem() != null) {
			textMorning.setText("  "+takeMeTour.getMorningItem().getName());
			textAfter.setText("  "+takeMeTour.getAfternoonItem().getName());
			textNight.setText("  "+takeMeTour.getNightItem().getName());
			initImageButton(nightButton, takeMeTour.getNightItem());
			initImageButton(afterButton, takeMeTour.getAfternoonItem());
			initImageButton(morningButton, takeMeTour.getMorningItem());
		} else {
			System.out.println("NULL VALUES TMT");
		}
		
		morningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                Intent itemDetailsScreenIntent = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                TmtsGroupActivity parentActivity = (TmtsGroupActivity)getParent();
                itemDetailsScreenIntent.putExtra("name_item", takeMeTour.getMorningItem().getName());
                itemDetailsScreenIntent.putExtra("item_description", takeMeTour.getMorningItem().getDescription());
                itemDetailsScreenIntent.putExtra("item_site", takeMeTour.getMorningItem().getWebsite());
                itemDetailsScreenIntent.putExtra("item_lng", takeMeTour.getMorningItem().getLongitude());
                itemDetailsScreenIntent.putExtra("item_lat", takeMeTour.getMorningItem().getLatitude());
                itemDetailsScreenIntent.putExtra("item_number", takeMeTour.getMorningItem().getEmail());
                itemDetailsScreenIntent.putExtra("item_id", Integer.toString(takeMeTour.getMorningItem().getId()));
                itemDetailsScreenIntent.putExtra("item_object", takeMeTour.getMorningItem());
         		parentActivity.startChildActivity("ItemDetailsActivity", itemDetailsScreenIntent);
			}
		});
		
		afterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent itemDetailsScreenIntent = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                TmtsGroupActivity parentActivity = (TmtsGroupActivity)getParent();
                itemDetailsScreenIntent.putExtra("name_item", takeMeTour.getAfternoonItem().getName());
                itemDetailsScreenIntent.putExtra("item_description", takeMeTour.getAfternoonItem().getDescription());
                itemDetailsScreenIntent.putExtra("item_site", takeMeTour.getAfternoonItem().getWebsite());
                //itemDetailsScreenIntent.putExtra("item_number", objItem.getNumber());
                itemDetailsScreenIntent.putExtra("item_lng", takeMeTour.getAfternoonItem().getLongitude());
                itemDetailsScreenIntent.putExtra("item_lat", takeMeTour.getAfternoonItem().getLatitude());
                itemDetailsScreenIntent.putExtra("item_number", takeMeTour.getAfternoonItem().getEmail());
                itemDetailsScreenIntent.putExtra("item_id", Integer.toString(takeMeTour.getAfternoonItem().getId()));
                itemDetailsScreenIntent.putExtra("item_object", takeMeTour.getAfternoonItem());
         		parentActivity.startChildActivity("ItemDetailsActivity", itemDetailsScreenIntent);
			}
		});
		
		nightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent itemDetailsScreenIntent = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                TmtsGroupActivity parentActivity = (TmtsGroupActivity)getParent();
                itemDetailsScreenIntent.putExtra("name_item", takeMeTour.getNightItem().getName());
                itemDetailsScreenIntent.putExtra("item_description", takeMeTour.getNightItem().getDescription());
                itemDetailsScreenIntent.putExtra("item_site", takeMeTour.getNightItem().getWebsite());
                itemDetailsScreenIntent.putExtra("item_lng", takeMeTour.getNightItem().getLongitude());
                itemDetailsScreenIntent.putExtra("item_lat", takeMeTour.getNightItem().getLatitude());
                itemDetailsScreenIntent.putExtra("item_number", takeMeTour.getNightItem().getEmail());
                itemDetailsScreenIntent.putExtra("item_id", Integer.toString(takeMeTour.getNightItem().getId()));
                itemDetailsScreenIntent.putExtra("item_object", takeMeTour.getNightItem());
         		parentActivity.startChildActivity("ItemDetailsActivity", itemDetailsScreenIntent);				
			}
		});
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tmt_activity);
		setHeader("Take Me a Tour", true, true);
		loadDataTmt();		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
		loadDataTmt();
	}
	
	@SuppressWarnings("deprecation")
	public void initImageButton(ImageButton imageButton, Item item) {
		if (item.getImageOne() == null) {
			imageButton.setBackgroundResource(R.drawable.unkown);
		} else {
			byte[] blob = item.getImageOne();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			if (bmp != null) {
				Drawable drawable = new BitmapDrawable(getResources(), bmp);
				imageButton.setBackgroundDrawable(drawable);
			} else {
				imageButton.setBackgroundResource(R.drawable.unkown);
			}
		}
	}
}