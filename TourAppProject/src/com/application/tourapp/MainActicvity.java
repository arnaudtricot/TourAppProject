package com.application.tourapp;

import com.application.tourapp.dal.DesignDAL;
import com.application.tourapp.dal.TypeDAL;
import com.application.tourapp.databasehandler.TourAppDatabaseHandler;
import com.application.tourapp.databasehandler.TypesDatabaseHandler;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.Design;
import com.application.tourapp.model.Type;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * La classe qui commence les activite Main de l'application 
 * 
 * @author Islam
 *
 */

@SuppressWarnings("deprecation")
public class MainActicvity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		String ITEMS_SPEC = getResources().getString(R.string.MenuOne);
		String MESSAGES_SPEC = getResources().getString(R.string.MenuTwo);
		String TMT_SPEC = getResources().getString(R.string.MenuThree);
		String SETTINGS_SPEC = getResources().getString(R.string.MenuFour);
		String FAVORITES_SPEC = getResources().getString(R.string.MenuFive);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		GenericActivity.setTabActivity(this);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutMain);
		Design design = new DesignDAL(getApplicationContext()).getDesignByName(TourAppGlobal.ERGO_APPLICATION);
		TabHost tabHost = getTabHost();
		if (design.getColorBackground() != null) {
			tabHost.setBackgroundColor(Color.parseColor("#"+design
					.getColorBackground()));
		}

		TabSpec itemsSpec = tabHost.newTabSpec(ITEMS_SPEC);
		itemsSpec.setIndicator(ITEMS_SPEC,
				getResources().getDrawable(R.drawable.icon_inbox));
		Intent itemsIntent = new Intent(this, TabGroupItemsActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		itemsSpec.setContent(itemsIntent);

		TabSpec messagesSpec = tabHost.newTabSpec(MESSAGES_SPEC);
		messagesSpec.setIndicator(MESSAGES_SPEC,
				getResources().getDrawable(R.drawable.icon_outbox));
		Intent messagesIntent = new Intent(this, TabGroupMessagesActivity.class);
		messagesSpec.setContent(messagesIntent);

		TabSpec tmtSpec = tabHost.newTabSpec(TMT_SPEC);
		tmtSpec.setIndicator(TMT_SPEC,
				getResources().getDrawable(R.drawable.icon_param));
		Intent tmtIntent = new Intent(this, TabGroupTmtActivity.class);
		tmtSpec.setContent(tmtIntent);

		TabSpec settingsSpec = tabHost.newTabSpec(SETTINGS_SPEC);
		settingsSpec.setIndicator(SETTINGS_SPEC,
				getResources().getDrawable(R.drawable.icon_profile));
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		settingsSpec.setContent(settingsIntent);

		TabSpec favoritesSpec = tabHost.newTabSpec(FAVORITES_SPEC);
		favoritesSpec.setIndicator(FAVORITES_SPEC,
				getResources().getDrawable(R.drawable.icon_favo));
		Intent favoritesIntent = new Intent(this,
				TabGroupFavoritsActivity.class);
		favoritesSpec.setContent(favoritesIntent);

		tabHost.addTab(itemsSpec);
		tabHost.addTab(messagesSpec);
		tabHost.addTab(tmtSpec);
		tabHost.addTab(settingsSpec);
		tabHost.addTab(favoritesSpec);
		
		/*TypeDAL nouveauType = new TypeDAL(getApplicationContext());
		Type type = new Type();
		type.setName("Test ajout type");
		nouveauType.addType(type);
		Log.v("Test", "AjoutType");*/
	}

	/**
	 * Une fonction special a cette activite qui necessite des traitement
	 * special
	 * 
	 * @param title
	 * @param btnHomeVisible
	 * @param btnFeedbackVisible
	 */
	public void setHeader(String title, boolean btnHomeVisible,
			boolean btnFeedbackVisible) {
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeaderMain);
		View inflated = stub.inflate();

		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
		txtTitle.setText(title);

		Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
		if (!btnHomeVisible)
			btnHome.setVisibility(View.INVISIBLE);

		Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
		if (!btnFeedbackVisible)
			btnFeedback.setVisibility(View.INVISIBLE);

	}
}