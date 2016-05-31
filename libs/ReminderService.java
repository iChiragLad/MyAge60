import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import message.Sender;

import constants.StringHelper;

import database.ConnectionManager;

public class ReminderService {
	public static void main(String[] args) {
		Timer t = new Timer();
		TimerTask ts = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				remindService();
			}
		};
		t.scheduleAtFixedRate(ts, 0, 60 * 60 * 1000);
	}

	public static void remindService() {

		String query = "SELECT * FROM userdetails where ( attendanceYN='Y' and HOUR(TIMEDIFF(CURRENT_TIMESTAMP, adate))>36 )||(HOUR(TIMEDIFF(CURRENT_TIMESTAMP, adate))<24 and attendanceYN='N')";
		// userID, username, pass, contact, contact1, contact2, attendence,
		// adate, imei, attendanceYN
		List list = ConnectionManager.getMapList(query);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			HashMap row = (HashMap) iterator.next();
			String username = StringHelper.n2s(row.get("username"));
			String contact = StringHelper.n2s(row.get("contact"));
			String contact1 = StringHelper.n2s(row.get("contact1"));
			String contact2 = StringHelper.n2s(row.get("contact2"));
			String attendanceYN = StringHelper.n2s(row.get("attendanceYN"));
			String message = "";
			if (attendanceYN.equalsIgnoreCase("Y")) {
				message = "Alert: "
						+ username
						+ " has not marked his presence since last 36 hours. Please call and check.";
			} else {
				message = "Alert: "
						+ username
						+ " is not in good health condition in last 24 hours. Please call and confirm.";
			}
			try {
				System.out.println("SENDING SMS");
				System.out.println("Contact No "+contact1);
				System.out.println("message "+message);
				Sender s = new Sender(contact1, message);
				s.send();
				Thread.sleep(10*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
