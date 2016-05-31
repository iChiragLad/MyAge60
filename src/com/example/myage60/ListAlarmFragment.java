package com.example.myage60;


import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListAlarmFragment extends Fragment {

	TextView empty;
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	public PendingIntent alarmIntent;
	public AlarmManager alarmMgr;

	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> user_med = new ArrayList<String>();
	private ArrayList<String> user_des = new ArrayList<String>();
	private ArrayList<String> user_hrs = new ArrayList<String>();
	private ArrayList<String> user_min = new ArrayList<String>();
	private ArrayList<String> user_int = new ArrayList<String>();

	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_list_alarm_fragment,container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		userList = (ListView) getActivity().findViewById(R.id.List);
		empty = (TextView) getActivity().findViewById(R.id.empty);
		mHelper = new DbHelper(getActivity());
		displayData();
		//long click to delete data
				userList.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							final int arg2, long arg3) {
						
						int idi = Integer.parseInt(userId.get(arg2));
						String med = user_med.get(arg2);
						String des = user_des.get(arg2);
						
						Intent i = new Intent(getActivity(), MyBB.class);
						i.putExtra("id", idi);
						i.putExtra("medi", med);
						i.putExtra("description", des);
						
						alarmIntent = PendingIntent.getBroadcast(getActivity(), Integer.parseInt(user_min.get(arg2)), i, 0);
						
						build = new AlertDialog.Builder(getActivity());
						build.setTitle("Delete reminder for " + user_med.get(arg2));
						build.setMessage("Do you want to delete ?");
						build.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										
										alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
										alarmMgr.cancel(alarmIntent);
										
										Toast.makeText(getActivity(),"Reminder for " + user_med.get(arg2)+ " is deleted and alarm cancelled", Toast.LENGTH_LONG).show();

										dataBase.delete(
												DbHelper.TABLE_NAME,
												DbHelper.KEY_ID + "="
														+ userId.get(arg2), null);
										displayData();
										dialog.cancel();
									}
								});

						build.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});
						AlertDialog alert = build.create();
						alert.show();

						return true;
					}
				});
		userList.setEmptyView(empty);
	}
	
	
	/**
	 * displays data from SQLite
	 */
	private void displayData() {
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.TABLE_NAME, null);

		userId.clear();
		user_med.clear();
		user_des.clear();
		user_hrs.clear();
		user_min.clear();
		user_int.clear();
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
				user_med.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.C_MED)));
				user_des.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.C_DES)));
				user_hrs.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.C_HRS)));
				user_min.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.C_MIN)));
				user_int.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.C_INT)));

			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(getActivity(),userId, user_med, user_des, user_hrs, user_min, user_int );
		userList.setAdapter(disadpt);
		mCursor.close();
	}

	
	/*
	
	ListView lv;
	TextView empty;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_list_alarm_fragment,container, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		empty = (TextView)getActivity().findViewById(R.id.Empty);
		lv = (ListView)getActivity().findViewById(R.id.listViewAlarms);
		DbHelper dbHelper = new DbHelper(getActivity());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		Cursor c = db.query(DbHelper.TABLE_NAME, new String[] {DbHelper.C_ID, DbHelper.C_MED, DbHelper.C_DES, DbHelper.C_HRS,DbHelper.C_MIN, DbHelper.C_INT}, null, null, null, null, null);
		getActivity().startManagingCursor(c);
		String[] from = {DbHelper.C_MED, DbHelper.C_DES, DbHelper.C_HRS, DbHelper.C_MIN, DbHelper.C_INT};
		int[] to = {R.id.tv1,R.id.tv3,R.id.tv2,R.id.tv4,R.id.tv5};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.newrow, c, from, to);
		
		lv.setAdapter(adapter);
		lv.setEmptyView(empty);
	}
	
*/
}