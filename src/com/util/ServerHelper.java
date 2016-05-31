package com.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;

//import com.example.myage60.QuickPrefsActivity;

import android.content.Context;
//import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ServerHelper {
	Context context;
	public ServerHelper(Context mContext) {
		// TODO Auto-generated constructor stub
		this.context=mContext;
	}
	public String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		System.out.println("Device IMEI is " + imei);
		return imei;

	}
	public boolean checkConnectivity(){
		boolean success=checkConnectivityServer(AndroidConstants.MAIN_SERVER_IP, Integer.parseInt(AndroidConstants.MAIN_SERVER_PORT));
		if(!success){
			SharedPreferences mPrefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			
			String ip=mPrefs.getString("ip", AndroidConstants.MAIN_SERVER_IP);
			String port=mPrefs.getString("port", AndroidConstants.MAIN_SERVER_PORT);
			AndroidConstants.MAIN_SERVER_IP=ip;
			AndroidConstants.MAIN_SERVER_PORT=port;
			success=checkConnectivityServer(AndroidConstants.MAIN_SERVER_IP, Integer.parseInt(AndroidConstants.MAIN_SERVER_PORT));
			if(!success){
			
				return false;
			}else{
				return true;
			}
			
		}else{
			return true;
		}
		
	}
	public void updateAttendance(String status){
		
		String url=AndroidConstants.MAIN_URL()
				+"methodId=updateAttendence&"
				+"status="+status+"&imei="+getIMEI();
		System.out.println(url);
		@SuppressWarnings("unused")
		String str=RemoteConnect.connectToServer(url);
		
		
	}
	public void updateToServer(){
		
	
		SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		@SuppressWarnings("deprecation")
		String username= URLEncoder.encode(mPrefs.getString("a","")) ;
		String password= mPrefs.getString("b","");
		String contact= mPrefs.getString("d","");
		String contact1= mPrefs.getString("e","");
		String contact2= mPrefs.getString("f","");
		String attendence = mPrefs.getString("g","");
		
		String imei=getIMEI();
		String url=AndroidConstants.MAIN_URL()
				+"methodId=checkUserDetails&"
				+"imei="+imei;
		System.out.println(url);
		String str=RemoteConnect.connectToServer(url);
		if (str.equalsIgnoreCase("false")) {
			toast("User does not exist on server.");
			url = AndroidConstants.MAIN_URL() + "methodId=insertUserDetails&"
					+ "imei=" + imei.trim() + "&username=" + username.trim() + "&"
					+ "password=" + password.trim() + "&" + "contact=" + contact.trim() + "&"
					+ "contact1=" + contact1.trim() + "&" + "contact2=" + contact2.trim()+"&attendence="+attendence.trim();
			System.out.println(url);
			str = RemoteConnect.connectToServer(url);
			if (str.equalsIgnoreCase("true")) {
				toast("Success");
			} else {
				toast("Failure");
			}
		}else{
			toast("User Already Registered.");
		}
		
	}
	public void toast(String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.show();
	}
	public static boolean checkConnectivityServer(String ip, int port) {
		boolean success = false;
		try {
			System.out.println("Checking Connectivity With "+ip+" "+port);
			Socket soc = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(ip, port);
			soc.connect(socketAddress, 3000);
			success = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(" Connecting to server " + success);
		return success;

	}
}

