package com.application.tourapp;

import android.app.Activity;
import android.app.TabActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.webservice.CityListWebService;
import com.application.tourapp.webservice.DesignWebService;
import com.application.tourapp.webservice.TypesListWebService;

/**
 * Une classe generique qui consiste a modifier le header toute en chargeant le
 * logo
 * 
 * @author Islam
 * 
 */

@SuppressWarnings("deprecation")
public class GenericActivity extends Activity {
	/** Called when the activity is first created. */

	static TabActivity tabActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setHeader(String title, boolean btnHomeVisible,
			boolean btnFeedbackVisible) {
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
		View inflated = stub.inflate();

		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
		txtTitle.setText(title);

		DesignDAL designDAL = new DesignDAL(getApplicationContext());
		Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
		if (designDAL.getDesignByName(TourAppGlobal.ERGO_APPLICATION)
				.getLogoApplication() == null) {
			btnHome.setBackgroundResource(R.drawable.logo_ta_2);
		} else {
			byte[] blob = designDAL.getDesignByName(
					TourAppGlobal.ERGO_APPLICATION).getLogoApplication();
			Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);

			if (bmp != null) {
				Drawable drawable = new BitmapDrawable(getResources(), bmp);
				btnHome.setBackgroundDrawable(drawable);
			} else {
				btnHome.setBackgroundResource(R.drawable.logo_ta_2);
			}
		}

		if (!btnHomeVisible)
			btnHome.setVisibility(View.INVISIBLE);

		Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
		if (!btnFeedbackVisible)
			btnFeedback.setVisibility(View.INVISIBLE);

		Button btnConnect = (Button) inflated.findViewById(R.id.btnConnect);
		Button btnDec = (Button) inflated.findViewById(R.id.btnDeconnect);
		if (!TourAppGlobal.isOnline(getApplicationContext())) {
			btnConnect.setVisibility(View.INVISIBLE);
			btnDec.setVisibility(View.INVISIBLE);
		} else {
			if (TourAppGlobal.IS_CONNECT) {
				btnDec.setVisibility(View.INVISIBLE);
			} else {
				btnConnect.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * Home button click handler
	 * 
	 * @param v
	 */
	public void btnHomeClick(View v) {
		getTabActivity().getTabHost().setCurrentTab(0);
	}

	/**
	 * Feedback button click handler
	 * 
	 * @param v
	 */
	public void btnFeedbackClick(View v) {
		getTabActivity().getTabHost().setCurrentTab(4);
	}

	/**
	 * Connect button click handler
	 * 
	 * @param v
	 */
	public void btnConnectClick(View v) {
		// new MessagesListWebService().fillMessages(v.getContext());
		// new TakeMeTourListWebService().fillTakeMeTour(v.getContext());
		// new TypesListWebService().fillListType(v.getContext());
		if (TourAppGlobal.IS_CONNECT) {
			TourAppGlobal.IS_CONNECT = false;
		} else {
			TourAppGlobal.IS_CONNECT = true;
			new CityListWebService().fillListCity(v.getContext());
			new DesignWebService().fillDesign(v.getContext());
			new TypesListWebService().fillListType(v.getContext());
		}
		getTabActivity().getTabHost().setCurrentTab(4);
		getTabActivity().getTabHost().setCurrentTab(0);
	}

	public static TabActivity getTabActivity() {
		return tabActivity;
	}

	public static void setTabActivity(TabActivity tabActivity) {
		GenericActivity.tabActivity = tabActivity;
	}
	
	public void btnGaucheTypeClick(View v) {
		TourAppGlobal.INDICE_TYPE--;
		getTabActivity().getTabHost().setCurrentTab(4);
		getTabActivity().getTabHost().setCurrentTab(0);
	}
	
	public void btnDroiteTypeClick(View v) {
		TourAppGlobal.INDICE_TYPE++;
		getTabActivity().getTabHost().setCurrentTab(4);
		getTabActivity().getTabHost().setCurrentTab(0);
	}
}
