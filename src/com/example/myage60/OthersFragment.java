package com.example.myage60;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class OthersFragment extends Fragment implements OnClickListener {

	ImageButton buttonAlarm, buttonLocation, buttonNews, buttonAttendance;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_others_fragment,container, false);

		buttonAlarm = (ImageButton) view.findViewById(R.id.othersReminderButton);
		buttonAlarm.setOnClickListener(this);
		buttonLocation = (ImageButton) view.findViewById(R.id.othersLocationButton);
		buttonLocation.setOnClickListener(this);
		buttonNews = (ImageButton) view.findViewById(R.id.othersNewsButton);
		buttonNews.setOnClickListener(this);
		buttonAttendance = (ImageButton) view.findViewById(R.id.othersAttendanceButton);
		buttonAttendance.setOnClickListener(this);

		return view;
		// return inflater.inflate(R.layout.activity_emergency_fragment,
		// container,false);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.othersReminderButton:

			Intent intent = new Intent(this.getActivity(), AlarmActivity.class);
			startActivity(intent);
			break;
		case R.id.othersLocationButton:
			Toast.makeText(getActivity(),"Available in Premium version of the APP",Toast.LENGTH_LONG).show();
			break;
		case R.id.othersAttendanceButton:
			Intent in = new Intent(this.getActivity(), ListAttendance.class);
			startActivity(in);
			break;
		case R.id.othersNewsButton:
			Toast.makeText(getActivity(),"Available in Premium version of the APP",Toast.LENGTH_LONG).show();
			break;
		}
	}

}
