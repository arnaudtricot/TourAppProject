package com.application.tourapp.baseadapter;

import java.util.List;

import com.application.tourapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.app.Activity;
import android.support.v4.view.ViewPager;


public class ImageSwipeAdapter extends PagerAdapter{
	Context context;
	public  List<byte[]> GalImages;
	 
	public ImageSwipeAdapter(Context context, List<byte[]> listImage){
		GalImages = listImage;
		this.context=context;
	}
	@Override
	public int getCount() {
		return GalImages.size();
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(context);
		//attention
		int padding = context.getResources().getDimensionPixelSize(R.dimen.text_size_medium);
		imageView.setPadding(padding, padding, padding, padding);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		Bitmap bmp = BitmapFactory.decodeByteArray(GalImages.get(position), 0, GalImages.get(position).length);
		imageView.setImageBitmap(bmp);
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == ((ImageView) object);
	}
}
