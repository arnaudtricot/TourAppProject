package com.application.tourapp.baseadapter;

import java.util.ArrayList;

import android.content.Context;
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
import com.application.tourapp.model.Message;

public class MessageListBaseAdapter extends BaseAdapter {
	private static ArrayList<Message> ItemrrayList;
	
	private Context context;
	
	private LayoutInflater l_Inflater;

	public MessageListBaseAdapter(Context context, ArrayList<Message> results) {
		ItemrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		return ItemrrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return ItemrrayList.get(position);
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
			convertView = l_Inflater.inflate(R.layout.item_messages_view, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
			
			Design design = new DesignDAL(context)
			.getDesignByName(TourAppGlobal.ERGO_APPLICATION);
			holder.txt_itemName.setTextColor(Color.parseColor("#"+design
					.getColorTitleItem()));
			holder.txt_itemDescription.setTextColor(Color.parseColor("#"+design
					.getColorDescriptionItem()));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		/*
    	 * EGA - la modification des attribue de la classe des types
    	 */
		
		holder.txt_itemName.setText(ItemrrayList.get(position).getSubject());
		holder.txt_itemDescription.setText(ItemrrayList.get(position).getText());
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
