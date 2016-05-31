package com.example.myage60;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.telephony.TelephonyManager;
import android.text.InputType;
//import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

//import com.util.AndroidConstants;
//import com.util.RemoteConnect;
import com.util.ServerHelper;

public class AttendanceDialog extends Activity {

	public static final String yes = "YES";
	public static final String no = "NO";
	 private static final int TEXT_ID = 0;
	 SharedPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_dialog);
		 android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
			android.os.StrictMode.setThreadPolicy(tp);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Register your presence");

		// Setting Dialog Message
		alertDialog.setMessage("By entering your password and hitting YES you assure that every thing is going good with you. If thats not the case hit no!!");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_action_user);
		
		// Use an EditText view to get user input.
        final EditText input = new EditText(this);
        input.setId(TEXT_ID);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertDialog.setView(input);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				
				//verifying entered password with the shared preference value
				mPrefs = PreferenceManager.getDefaultSharedPreferences(AttendanceDialog.this);
				String pass = mPrefs.getString("b", "");
				String value = input.getText().toString();
				
				if (pass.equals(value)){
					
					//open database
					DbHelper dbHelper = new DbHelper(getApplicationContext());
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					
					//insert in database
					ContentValues values = new ContentValues();
					values.put(DbHelper.C_DAY, calendar.get(Calendar.DAY_OF_MONTH));
					values.put(DbHelper.C_MNT, calendar.get(Calendar.MONTH) + 1);
					values.put(DbHelper.C_YR, calendar.get(Calendar.YEAR));
					values.put(DbHelper.C_VAL, yes);
					db.insert(DbHelper.TABLE_NAME_AT, null, values);
					
					//close database
					db.close();
					dbHelper.close();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ServerHelper sh=new ServerHelper(AttendanceDialog.this);
				        	sh.updateAttendance("Y");
						}
					});
					
				}else{
					
					//open database
					DbHelper dbHelper = new DbHelper(getApplicationContext());
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					
					//insert in database
					ContentValues values = new ContentValues();
					values.put(DbHelper.C_DAY, calendar.get(Calendar.DAY_OF_MONTH));
					values.put(DbHelper.C_MNT, calendar.get(Calendar.MONTH) + 1);
					values.put(DbHelper.C_YR, calendar.get(Calendar.YEAR));
					values.put(DbHelper.C_VAL, no);
					db.insert(DbHelper.TABLE_NAME_AT, null, values);
					
					//close database
					db.close();
					dbHelper.close();
					Toast.makeText(getApplicationContext(), "Password did not match!!", Toast.LENGTH_SHORT).show();
					
				}

			
	
				finish();
			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,	int which) {
		
				//open database
				DbHelper dbHelper = new DbHelper(getApplicationContext());
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				
				//insert in database
				ContentValues values = new ContentValues();
				values.put(DbHelper.C_DAY, calendar.get(Calendar.DAY_OF_MONTH));
				values.put(DbHelper.C_MNT, calendar.get(Calendar.MONTH) + 1);
				values.put(DbHelper.C_YR, calendar.get(Calendar.YEAR));
				values.put(DbHelper.C_VAL, no);
				db.insert(DbHelper.TABLE_NAME_AT, null, values);
				
				//close database
				db.close();
				dbHelper.close();

			dialog.cancel();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ServerHelper sh=new ServerHelper(AttendanceDialog.this);
		        	sh.updateAttendance("N");
				}
			});
			finish();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
}
