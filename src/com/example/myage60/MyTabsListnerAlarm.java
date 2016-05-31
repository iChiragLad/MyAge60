package com.example.myage60;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

public class MyTabsListnerAlarm implements TabListener {

	public Fragment fragment;
	public Context context;
	
	public MyTabsListnerAlarm(Fragment fragment, Context context){
		this.fragment = fragment;
		this.context = context;
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		
		ft.replace(R.id.containeralarm, fragment);
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
		ft.remove(fragment);
		
	}


}
