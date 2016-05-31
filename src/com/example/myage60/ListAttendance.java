package com.example.myage60;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListAttendance extends Activity {

	ListView lv;
	TextView empty;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_attendance);
		
		empty = (TextView)findViewById(R.id.emptyAttendance);
		lv = (ListView)findViewById(R.id.listViewAttendance);
		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		Cursor c = db.query(DbHelper.TABLE_NAME_AT, new String[] {DbHelper.C_ID, DbHelper.C_DAY, DbHelper.C_MNT, DbHelper.C_YR,DbHelper.C_VAL}, null, null, null, null, null);
		startManagingCursor(c);
		String[] from = {DbHelper.C_DAY, DbHelper.C_MNT, DbHelper.C_YR, DbHelper.C_VAL};
		int[] to = {R.id.textViewDay, R.id.textViewMonth, R.id.textViewYear, R.id.textViewValue};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.newrowtwo, c, from, to);
		
		lv.setAdapter(adapter);
		lv.setEmptyView(empty);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_attendance, menu);
		return true;
	}

}
