package com.example.myage60;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SystemRestartBB extends BroadcastReceiver {
	
	SharedPreferences mPrefs;
	private AlarmManager alarmMgr, alarmMgrFRat;
	private PendingIntent alarmIntent, alarmIntentFRat;
    @Override
    public void onReceive(Context context, Intent intent) {
    	
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        	
        	try {
				//Intent i = new Intent(context, AttendanceDialog.class);
				//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//context.startActivity(i);
        		
        		
        		DbHelper dbHelper = new DbHelper(context);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				Cursor c = db.query(DbHelper.TABLE_NAME, null, null, null, null, null, null);
				int idi;
				String med;
				String des;
				int hrs;
				int min;
				int in;
				while(c.moveToNext()){
					idi = c.getInt(0);
					med = c.getString(1);
					des = c.getString(2);
					hrs = c.getInt(3);
					min = c.getInt(4);
					in = c.getInt(6);
					
						alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
						Intent i = new Intent(context, MyBB.class);
						i.putExtra("id", idi);
						i.putExtra("medi", med);
						i.putExtra("description", des);
						alarmIntent = PendingIntent.getBroadcast(context, min, i, 0);
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(System.currentTimeMillis());
						calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), hrs, min, 0);
						//calendar.set(Calendar.HOUR_OF_DAY,12 );
						//calendar.set(Calendar.MINUTE, 54);
						alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
						calendar.getTimeInMillis(), 1000 * 60 * in, alarmIntent);
			
				}	
        		
        		c.close();
        		dbHelper.close();
        		db.close();
        		
        		
        		mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
				String txt7 = mPrefs.getString("g",null);
				
				int hr = Integer.parseInt(txt7);
        		// Attendance system alarm firing
				alarmMgrFRat = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        	Intent i = new Intent(context, AttendanceDialog.class);
	        	alarmIntentFRat = PendingIntent.getActivity(context, 0, i, 0);
	        	// Set the alarm to start at approximately --:-- a.m/p.m.
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTimeInMillis(System.currentTimeMillis());
	        	calendar.set(Calendar.HOUR_OF_DAY, hr);
	        	calendar.set(Calendar.MINUTE, 00);
	        	calendar.set(Calendar.SECOND, 00);
	        	// With setInexactRepeating(), you have to use one of the AlarmManager interval
	        	// constants--in this case, AlarmManager.INTERVAL_DAY.
	        	alarmMgrFRat.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
	        	        AlarmManager.INTERVAL_DAY, alarmIntentFRat);
	        	
			} catch (Exception e) {
				Toast.makeText(context, "Entered Exception", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
        }
    }
}