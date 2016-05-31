package com.example.myage60;

import com.util.ServerHelper;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
public class Home extends Activity {
	ServerHelper sh=null;
	SharedPreferences mPrefs;
	final String welcomeScreenShownPref = "welcomeScreenShown";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		 android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
			android.os.StrictMode.setThreadPolicy(tp);
		 sh=new ServerHelper(getApplicationContext());
	
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		 if(!sh.checkConnectivity()){
				Intent i=new Intent(getApplicationContext(), QuickPrefsActivity.class);
				
				startActivity(i);
				return;
			}
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		// second argument is the default to use if the preference can't be
		// found
		Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref,
				false);
		if (!welcomeScreenShown) {

			
			// here you can launch another activity if you like
			// the code below will display a popup
			SharedPreferences.Editor editor = mPrefs.edit();
			editor.putBoolean(welcomeScreenShownPref, true);
			editor.commit();
			Intent i = new Intent(this, Welcome.class);
			startActivity(i);
			finish();
			// Very important to save the preference
		} else {

			
			// Intent i = new Intent(this,Register.class);
			// startActivity(i);

			ActionBar actionbar = getActionBar();

			getActionBar().setDisplayHomeAsUpEnabled(true);

			actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			ActionBar.Tab mEmergency = actionbar.newTab().setText(
					getString(R.string.first_tab));
			ActionBar.Tab mOthers = actionbar.newTab().setText(
					getString(R.string.second_tab));

			Fragment mEmergencyFragment = new EmergencyFragment();
			Fragment mOthersFragment = new OthersFragment();

			mEmergency.setTabListener(new MyTabsListner(mEmergencyFragment,
					getApplicationContext()));
			mOthers.setTabListener(new MyTabsListner(mOthersFragment,
					getApplicationContext()));

			actionbar.addTab(mEmergency);
			actionbar.addTab(mOthers);
			
			Toast.makeText(this, "entered else", Toast.LENGTH_LONG).show();

		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		
		case R.id.actionbarAbout:
			startActivity(new Intent(Home.this, About.class));
			return true;
		case R.id.actionbarSystemSettings:
			startActivity(new Intent(Home.this, QuickPrefsActivity.class));
			return true;
		case R.id.itemShowDialog:
			startActivity(new Intent(Home.this, AttendanceDialog.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}