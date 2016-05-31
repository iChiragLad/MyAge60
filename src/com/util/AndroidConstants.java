package com.util;


public class AndroidConstants {

	public static String MAIN_SERVER_IP = "192.168.0.102";

	public static String MAIN_SERVER_PORT = "8080";

	public static final String MAIN_URL() {
		return "http://" + AndroidConstants.MAIN_SERVER_IP + ":"
				+ AndroidConstants.MAIN_SERVER_PORT
				+ "/MyAge60WebServer/pages/connect.jsp?";
	}

}
