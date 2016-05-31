package com.example.myage60;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> medName;
	private ArrayList<String> desName;
	private ArrayList<String> hrsName;
	private ArrayList<String> minName;
	private ArrayList<String> intName;
	

	public DisplayAdapter(Context c, ArrayList<String> id,ArrayList<String> medname, ArrayList<String> desname, ArrayList<String> hrsname, ArrayList<String> minname, ArrayList<String> intname) {
		this.mContext = c;

		this.id = id;
		this.medName = medname;
		this.desName = desname;
		this.hrsName = hrsname;
		this.minName = minname;
		this.intName = intname;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.newrow, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_medName = (TextView) child.findViewById(R.id.txt_medName);
			mHolder.txt_desName = (TextView) child.findViewById(R.id.txt_desName);
			mHolder.txt_hrsName = (TextView) child.findViewById(R.id.txt_hrsName);
			mHolder.txt_minName = (TextView) child.findViewById(R.id.txt_minName);
			mHolder.txt_intName = (TextView) child.findViewById(R.id.txt_intName);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_medName.setText(medName.get(pos));
		mHolder.txt_desName.setText(desName.get(pos));
		mHolder.txt_hrsName.setText(hrsName.get(pos));
		mHolder.txt_minName.setText(minName.get(pos));
		mHolder.txt_intName.setText(intName.get(pos));

		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_medName;
		TextView txt_desName;
		TextView txt_hrsName;
		TextView txt_minName;
		TextView txt_intName;
	}

}
