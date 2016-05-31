package com.example.myage60;

import java.util.Calendar;

import com.util.ServerHelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Setup extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	SharedPreferences mPrefs;
	String txt1=null,txt2=null,txt4=null,txt5=null,txt6=null;
	//public int count = 0;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.xml.setup);
       mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
   	   mPrefs.registerOnSharedPreferenceChangeListener(this);
   	 android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
		android.os.StrictMode.setThreadPolicy(tp);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_menu, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.save:
			mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String txt1 = mPrefs.getString("a",null);
			String txt2 = mPrefs.getString("b",null);
			String txt4 = mPrefs.getString("d",null);
			String txt5 = mPrefs.getString("e",null);
			String txt6 = mPrefs.getString("f",null);
			String txt7 = mPrefs.getString("g",null);
			//if (count>=6 /*&& (text2 != null) && (text3 != null) && (text4 != null) && (text5 != null) && (text6 != null)*/) 
			if ((txt1 != null) && (txt2 != null) && (txt4 != null) && (txt5 != null) && (txt6 != null) && (txt7 != null)) 
			{
				Toast.makeText(this, "Congratulations, registration done!!", Toast.LENGTH_SHORT).show();
				
				// Attendance system alarm firing
				alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	        	Intent i = new Intent(this, AttendanceDialog.class);
	        	alarmIntent = PendingIntent.getActivity(this, 0, i, 0);
	        	// Set the alarm to start at approximately --:-- a.m/p.m.
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTimeInMillis(System.currentTimeMillis());
	        	
	        	int hr = Integer.parseInt(txt7);
	        	
	        	calendar.set(Calendar.HOUR_OF_DAY, hr);
	        	calendar.set(Calendar.MINUTE, 00);
	        	calendar.set(Calendar.SECOND, 00);
	        	// With setInexactRepeating(), you have to use one of the AlarmManager interval
	        	// constants--in this case, AlarmManager.INTERVAL_DAY.
	        	alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
	        	        AlarmManager.INTERVAL_DAY, alarmIntent);
	        	
	        	runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ServerHelper sh=new ServerHelper(Setup.this);
			        	sh.updateToServer();
					}
				});
	        	//Go to home screen
				Intent intent = new Intent(Setup.this,Home.class);
				startActivity(intent);
				finish();
				return true;
			}else{
				Toast.makeText(this, "Looks as if a field is empty. Fill in all the details and then press save.", Toast.LENGTH_LONG).show();
				return true;
			}
			
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		
		String ex = sp.getString(key, null);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, ex);
		editor.commit();
		//ex = sp.getString(key, null);
		//count++;
	}

    
}