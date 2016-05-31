package com.example.myage60;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Context;
//import android.widget.Toast;


public class MyTabsListner implements TabListener {

	public Fragment fragment;
	public Context context;
	
	public MyTabsListner(Fragment fragment, Context context){
		this.fragment = fragment;
		this.context = context;
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		//Toast.makeText(context, "Re-selected", Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction ft) {
		
		//Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
		ft.replace(R.id.container, fragment);
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
		//Toast.makeText(context, "Un-selected", Toast.LENGTH_SHORT).show();
		ft.remove(fragment);
		
	}


}
