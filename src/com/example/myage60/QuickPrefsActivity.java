package com.example.myage60;

import com.util.AndroidConstants;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class QuickPrefsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	SharedPreferences mPrefs;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pre);
		 android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
			android.os.StrictMode.setThreadPolicy(tp);
		mPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		mPrefs.registerOnSharedPreferenceChangeListener(this);
		
		Editor edit= mPrefs.edit();
		edit.putString("ip", AndroidConstants.MAIN_SERVER_IP);
		edit.putString("port", AndroidConstants.MAIN_SERVER_PORT);
		edit.commit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		try{
		String ex = sp.getString(key, null);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, ex);
		System.out.println("Updating Key " + key);
		System.out.println("Updating Value" + ex);
		editor.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
