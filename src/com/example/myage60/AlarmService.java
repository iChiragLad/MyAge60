package com.example.myage60;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service{
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
				DbHelper dbHelper = new DbHelper(AlarmService.this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				Cursor c = db.query(DbHelper.TABLE_NAME, null, null, null, null, null, null);
				
				int idi;
				String med;
				String des;
				int hrs;
				int min;
				int rept;
				int in;
				
				c.moveToPosition(c.getCount() - 1);
					idi = c.getInt(0);
					med = c.getString(1);
					des = c.getString(2);
					hrs = c.getInt(3);
					min = c.getInt(4);
					rept = c.getInt(5);
					in = c.getInt(6);
						alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
						Intent i = new Intent(getApplicationContext(), MyBB.class);
						i.putExtra("id", idi);
						i.putExtra("medi", med);
						i.putExtra("description", des);
						alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), min, i, 0);
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(System.currentTimeMillis());
						calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), hrs, min, 0);
						//calendar.set(Calendar.HOUR_OF_DAY, hrs);
						//calendar.set(Calendar.MINUTE, min);
						if(rept == 1){
							alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
							calendar.getTimeInMillis(), 1000 * 60 * in, alarmIntent);
						}else{
							alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
						}
		
	}
}
