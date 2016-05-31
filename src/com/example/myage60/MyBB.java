package com.example.myage60;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBB extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent i) {
		
		int id = i.getIntExtra("id", 0);
		String medicine = i.getStringExtra("medi");
		String description = i.getStringExtra("description");
		
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.ic_launcher, "Time to take your Medicine", System.currentTimeMillis());
		note.setLatestEventInfo(context, "Time to take " + medicine, description, null);
		note.flags |= Notification.FLAG_AUTO_CANCEL;
		note.defaults |= Notification.DEFAULT_VIBRATE;
		note.defaults |= Notification.DEFAULT_SOUND;
		nm.notify(id, note);
	}
}
