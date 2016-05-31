package com.example.myage60;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;


public class AlarmActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		ActionBar actionbar = getActionBar();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab mAddAlarm = actionbar.newTab().setText(getString(R.string.alarm_tab_one));
		ActionBar.Tab mListAlarm = actionbar.newTab().setText(getString(R.string.alarm_tab_two));
		
		Fragment mAddAlarmFragment = new AddAlarmFragment();
		Fragment mListAlarmFragment = new ListAlarmFragment();
		
		mAddAlarm.setTabListener(new MyTabsListnerAlarm(mAddAlarmFragment,getApplicationContext()));
		mListAlarm.setTabListener(new MyTabsListnerAlarm(mListAlarmFragment,getApplicationContext()));
		
		actionbar.addTab(mAddAlarm);
		actionbar.addTab(mListAlarm);				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    return super.onOptionsItemSelected(item);
	}
	
}

