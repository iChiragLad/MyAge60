package com.example.myage60;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class EmergencyFragment extends Fragment implements OnClickListener{
	
	SharedPreferences mPrefs;
	SharedPreferences dump;
	GPSTracker mGPS;
	ImageButton btn;
	ProgressDialog pd;
	View topLevelLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_emergency_fragment, container, false);
	        
	        btn = (ImageButton) view.findViewById(R.id.emmButton);
	        btn.setOnClickListener(this);      
	        

	        return view;
		//return inflater.inflate(R.layout.activity_emergency_fragment, container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try{
		dump = getActivity().getSharedPreferences("shared_preferences_test", Context.MODE_PRIVATE);
		topLevelLayout = getActivity().findViewById(R.id.top_layout);
		if (isFirstTime()) {
				topLevelLayout.setVisibility(View.INVISIBLE);
			}
		mGPS = new GPSTracker(this.getActivity());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Check of SP for overlay screen
	 private boolean isFirstTime(){
	   
		 Boolean ranBefore = dump.getBoolean("chk", false);
		 if (!ranBefore) {
	    	 
	    	 SharedPreferences.Editor editor = dump.edit();
	    	 editor.putBoolean("chk", true);
	    	   editor.commit();
	    	 topLevelLayout.setVisibility(View.VISIBLE);
	    	 topLevelLayout.setOnTouchListener(new View.OnTouchListener(){
	    
	    		 @Override
	    		 public boolean onTouch(View v, MotionEvent event) {
	    			 topLevelLayout.setVisibility(View.INVISIBLE);
	    			 return false;
	    		 }
	    	 });
	     }
	    return ranBefore;
	   	}


	@Override
	public void onClick(View v) {	
		
		(new FindAddress(this.getActivity())).execute();
	    
	}
	
	//AsyncTask
	private class FindAddress extends AsyncTask<Void, Void, String>{
		
		Context mContext;
		ProgressDialog dp;
        public FindAddress(Context context) {
            super();
            mContext = context;
        }

		@Override
		protected String doInBackground(Void... arg0) {
			
		    double lat = mGPS.getLatitude();
		    double lng = mGPS.getLongitude();
		    Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		    try {
		        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
		        Address obj = addresses.get(0);
		        String add = obj.getAddressLine(0);
		        add = add + ",";
		        add = add +  obj.getLocality();
		        add = add + "-";
		        add = add + obj.getPostalCode();
		        add = add + "\n" + obj.getAdminArea();
		        add = add + "," + obj.getCountryName();
		        add = add + "(" + obj.getCountryCode() + ")";
		        
		       
		       return add;
		} catch (IOException e) {

			e.printStackTrace();
		    return e.getMessage();
		        
		}
		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dp = ProgressDialog.show(mContext, "Sending Message", "Fetching current Location....");
			dp.show();
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			try {
				double latitude = mGPS.getLatitude();
				double longitude = mGPS.getLongitude();
				mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
				String ex = mPrefs.getString("e", "");
				String nm = mPrefs.getString("a", "");
				SmsManager.getDefault().sendTextMessage(ex, null, nm +" is in Emergency!!\n"+"Latitude:"+ latitude+ "Longitude :" +longitude + "\nAddress => \n" + result, null, null);
				dp.dismiss();
				Toast.makeText(mContext, "Your Location is - \n\nLatitude: " + latitude +"\nLongitude:" + longitude + "\nAddress => \n\n" + result, Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				
				Toast.makeText(mContext, "Unable to send SMS. Check Your balance !!", Toast.LENGTH_LONG).show();
				dp.dismiss();
			}
		}

		
	}
		
	}