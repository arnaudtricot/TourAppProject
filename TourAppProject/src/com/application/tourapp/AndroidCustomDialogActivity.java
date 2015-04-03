package com.application.tourapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This is a test class for a dialog box
 * 
 * @author Emaleu
 * 
 */

public class AndroidCustomDialogActivity extends Activity {

	Button buttonOpenDialog;

	String KEY_TEXTPSS = "TEXTPSS";
	static final int CUSTOM_DIALOG_ID = 0;

	ListView dialog_ListView;

	String[] listContent = { "January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November",
			"December" };

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alo);

		View someView = findViewById(R.id.hopa);

		// Find the root view
		View root = someView.getRootView();

		// Set the color
		root.setBackgroundColor(Color.TRANSPARENT);

		/*
		 * buttonOpenDialog = (Button)findViewById(R.id.opendialog);
		 * buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){
		 * 
		 * @Override public void onClick(View arg0) {
		 */
		showDialog(CUSTOM_DIALOG_ID);
		// }});

	}

	@Override
	protected Dialog onCreateDialog(int id) {

		Dialog dialog = null;

		switch (id) {
		case CUSTOM_DIALOG_ID:
			dialog = new Dialog(AndroidCustomDialogActivity.this);
			dialog.setContentView(R.layout.dialoglayout);
			dialog.setTitle("Custom Dialog");

			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);

			dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					Toast.makeText(AndroidCustomDialogActivity.this,
							"OnDismissListener", Toast.LENGTH_LONG).show();
				}
			});

			// Prepare ListView in dialog
			dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, listContent);
			dialog_ListView.setAdapter(adapter);
			dialog_ListView.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Toast.makeText(
							AndroidCustomDialogActivity.this,
							parent.getItemAtPosition(position).toString()
									+ " clicked", Toast.LENGTH_LONG).show();
					dismissDialog(CUSTOM_DIALOG_ID);
				}
			});

			break;
		}

		return dialog;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
		super.onPrepareDialog(id, dialog, bundle);

		switch (id) {
		case CUSTOM_DIALOG_ID:
			break;
		}

	}

}
