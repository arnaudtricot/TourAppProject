package com.application.tourapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

//pour la galerie photos en dessous de la carte 
//cette classe ne sert plus mais attention avant de la supprimer
public class GalleryMap extends Activity{
	
	private final static int[] IMAGES = {R.drawable.egypte1,
										 R.drawable.egypte2,
		                                 R.drawable.egypte3
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//déclare l'interface graphique qu'utilise l'activité
		setContentView(R.layout.map_item_activity);
		//instanciation de l'objet gallery dans xml
		Gallery gallery = (Gallery) findViewById(R.id.galleryMaps);
		ImageAdapter adapter = new ImageAdapter(this, IMAGES);
		gallery.setAdapter(adapter);
		
		
		
		
	}
	public static class ImageAdapter extends BaseAdapter {
		private int[] m_images;
		private Context m_context;
		private int m_itemBackground;
		public ImageAdapter(Context context, int[] images) {
			m_context = context;
			m_images=images;
			TypedArray array =
			context.obtainStyledAttributes(R.styleable.Gallery1);
			m_itemBackground = array.getResourceId(
			R.styleable.Gallery1_android_galleryItemBackground, 0);
			array.recycle();
		}
		public int getCount() {
			return m_images.length;
		}
		public Object getItem(int position) {
			return position;
		}
		public long getItemId(int position) {
			return position;
		}
			
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView img = new ImageView(m_context);
			img.setImageResource(m_images[position]);
			//img.setScaleType(ImageView.ScaleType.FIT_XY);
			//img.setLayoutParams(new Gallery.LayoutParams(75, 75));
			img.setBackgroundResource(m_itemBackground);
			return img;
		}	
	}
	
}
