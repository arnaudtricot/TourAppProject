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
import com.application.tourapp.model.Type;

public class TypeListBaseAdapter extends BaseAdapter {
	private static ArrayList<Type> itemrrayList;

	private Integer[] imgid = { R.drawable.unkown };

	private LayoutInflater l_Inflater;
	private Context context;

	public TypeListBaseAdapter(Context context, ArrayList<Type> results) {
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
		/*
		 * EGA - la modification des attribue de la classe des types
		 */
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.type_details_view, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView
					.findViewById(R.id.name);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/*
		 * EGA - la modification des attribue de la classe des types
		 */

		holder.txt_itemName.setText(itemrrayList.get(position).getName());
		Design design = new DesignDAL(context)
				.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		holder.txt_itemName.setTextColor(Color.parseColor("#"+design
				.getColorTitleItem()));
		if (itemrrayList.get(position).getImageBlob() == null) {
			holder.itemImage.setImageResource(imgid[0]);
		} else {
			byte[] blob = itemrrayList.get(position).getImageBlob();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
			if (bmp != null) {
				holder.itemImage.setImageBitmap(bmp);
			} else {
				holder.itemImage.setImageResource(imgid[0]);
			}
		}

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
