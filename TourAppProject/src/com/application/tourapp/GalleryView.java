package com.application.tourapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;

import com.application.tourapp.model.Item;

/**
 * C'est une classe qui represente la Gallery de l'application pour représenter
 * les images sous forme d'une liste des images
 * 
 * @author Ramy
 * 
 */

@SuppressWarnings("deprecation")
public class GalleryView extends Activity {
	ArrayList<Bitmap> pics;
	ImageView imageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_gallery_view);
		LinearLayout layout = (LinearLayout) findViewById(R.id.itemGallery);
		Design design = new DesignDAL(getApplicationContext())
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		layout.setBackgroundColor(Color.parseColor("#"
				+ design.getColorBackground()));
		Item item = (Item) getIntent().getSerializableExtra("item_object");
		pics = new ArrayList<Bitmap>();
		for (byte[] bs : item.getListImages()) {
			byte[] blob = bs;
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			pics.add(bmp);
		}
		Gallery ga = (Gallery) findViewById(R.id.Gallery01);
		ga.setAdapter(new ImageAdapter(this));

		imageView = (ImageView) findViewById(R.id.ImageView01);
		ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*
				 * Toast.makeText( getBaseContext(),
				 * "You have selected picture " + (arg2 + 1) + " of Antartica",
				 * Toast.LENGTH_SHORT).show();
				 */
				imageView.setImageBitmap(pics.get(arg2));

			}

		});

	}

	public class ImageAdapter extends BaseAdapter {

		private Context ctx;
		int imageBackground;

		public ImageAdapter(Context c) {
			ctx = c;
			TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
			imageBackground = ta.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		@Override
		public int getCount() {

			return pics.size();
		}

		@Override
		public Object getItem(int arg0) {

			return arg0;
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ImageView iv = new ImageView(ctx);
			iv.setImageBitmap(pics.get(arg0));
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
			iv.setBackgroundResource(imageBackground);
			return iv;
		}

	}
}