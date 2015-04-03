package com.application.tourapp.baseadapter;

import java.util.Arrays;
import java.util.List;

import com.application.tourapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Cette classe est un adaptateur qui dérive de l'interface BaseAdapter
 * @author Modou
 * @version 1.0
 *
 */
public class ImageAdapter extends BaseAdapter{
	/**
	 * le contexte dans lequel est présent l'adapter
	 */
	private Context mContext;

	// Mettre les images dans un tableau
	//à récupérer de la base de données
	//reference to our images
	/**
	 * un tableau d'images
	 */
	public List<byte[]> mThumbIds;

	/**
	 * Constructeur de la classe ImageAdapter
	 * @param c
	 */
	public ImageAdapter(Context c, List<byte[]> listImage){
		mThumbIds = listImage;
		mContext = c;
	}

	/**
	 * @return le nombred 'items dans le tableau mThumbIds
	 */
	@Override
	public int getCount() {
		return mThumbIds.size();
	}

	/**
	 * @param position
	 * @return pour connaitre l'item situé à la position position
	 */
	@Override
	public Object getItem(int position) {
		return mThumbIds.get(position);
	}

	/**
	 * @param position
	 * @return l'identifiant d'un item en fonction de sa position position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	//create a new ImageView for each item referenced by the Adapter
	/**
	 * Cette méthode créer une nouvelle ImageView pour chaque item référencé par l'adapter
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return une ImageView pour l'item
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		Bitmap bmp = BitmapFactory.decodeByteArray(mThumbIds.get(position), 0, mThumbIds.get(position).length);
		imageView.setImageBitmap(bmp);
		//declares that images should be cropped toward the center (if necessary)
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		//sets the height and width for the view, then each images is resized and cropped to fit in these dimensions,
		//as appropriate
		imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
		return imageView;
	}

}

