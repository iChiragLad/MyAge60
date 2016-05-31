package com.example.myage60;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddAlarmFragment extends Fragment implements OnClickListener {
	
	ImageButton add;
	TimePicker tp;
	EditText med,des;
	//EditText interval;
	CheckBox cb;
	int rept = 0;
	Spinner myspinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_add_alarm_fragment,container, false);		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		add = (ImageButton)getActivity().findViewById(R.id.buttonAddAlarm);
		add.setOnClickListener(this);
		
		myspinner = (Spinner) getActivity().findViewById(R.id.spinner1);
		if(rept == 0){
			myspinner.getSelectedView();
			myspinner.setEnabled(false);
		}else{
			myspinner.getSelectedView();
			myspinner.setEnabled(true);
		}
		med = (EditText)getActivity().findViewById(R.id.medName);
		des = (EditText)getActivity().findViewById(R.id.des);
		//interval = (EditText)getActivity().findViewById(R.id.interval);
		tp = (TimePicker)getActivity().findViewById(R.id.timePicker);
		cb = (CheckBox)getActivity().findViewById(R.id.repeat);
		cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(cb.isChecked()){
					rept = 1;
					myspinner.getSelectedView();
					myspinner.setEnabled(true);
				}else{
					rept = 0;
					myspinner.getSelectedView();
					myspinner.setEnabled(false);
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
			
			String medi = med.getText().toString();
			String descrip = des.getText().toString();
			//int inter = Integer.parseInt(interval.getText().toString());
			int inter = Integer.parseInt(String.valueOf(myspinner.getSelectedItem()));
			int hrs = tp.getCurrentHour();
			int min = tp.getCurrentMinute();
			//open database
			DbHelper dbHelper = new DbHelper(getActivity());
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			
			//insert in database
			ContentValues values = new ContentValues();
			values.put(DbHelper.C_MED, medi);
			values.put(DbHelper.C_DES, descrip);
			values.put(DbHelper.C_HRS, hrs);
			values.put(DbHelper.C_MIN, min);
			values.put(DbHelper.C_REP, rept);
			values.put(DbHelper.C_INT, inter);
			
			db.insert(DbHelper.TABLE_NAME, null, values);
			Toast.makeText(getActivity(), "Data Inserted in Database", Toast.LENGTH_LONG).show();
			
			//close database
			db.close();
			dbHelper.close();
			
			Intent intent = new Intent(this.getActivity(),AlarmService.class);
			getActivity().startService(intent);
		
	}

}
