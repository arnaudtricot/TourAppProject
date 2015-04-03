package com.application.tourapp.baseadapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.tourapp.R;
import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Item;

public class ItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<Item> itemrrayList;
	
	private Context context;
	
	private Integer[] imgid = {
			R.drawable.unkown
	};
	
	private LayoutInflater l_Inflater;

	public ItemListBaseAdapter(Context context, ArrayList<Item> results) {
		itemrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		return itemrrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemrrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
			holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_itemName.setText(itemrrayList.get(position).getName());
		holder.txt_itemDescription.setText(itemrrayList.get(position).getDescription());
		holder.txt_itemPrice.setText(itemrrayList.get(position).getPrix());
		Design design = new DesignDAL(context)
		.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		holder.txt_itemName.setTextColor(Color.parseColor("#"+design
				.getColorTitleItem()));
		holder.txt_itemDescription.setTextColor(Color.parseColor("#"
				+ design.getColorDescriptionItem()));
		if (itemrrayList.get(position).getImageOne() == null) {
			holder.itemImage.setImageResource(imgid[0]);
		} else {
			byte[] blob = itemrrayList.get(position).getImageOne();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			if (bmp != null) {
				holder.itemImage.setImageBitmap(bmp);
			} else {
				holder.itemImage.setImageResource(imgid[0]);
			}
		}
		
//		imageLoader.DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg", holder.itemImage);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
