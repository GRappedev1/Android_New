package com.vinfotech.main;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Service class to update current network status
 * 
 * @author ramanands
 * 
 */
public class NetworkService extends Service {
	public static boolean online = false;

	@Override
	public void onCreate() {
		super.onCreate();

		registerReceiver(networkChangeReceiver, getNetworkChangeIntentFilter());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(networkChangeReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private synchronized void setOnline(boolean online) {
		NetworkService.online = online;
		Log.i("NetworkService", "setOnline - online=" + online);

		Intent intent = new Intent();
		intent.setAction("NetworkService");
		intent.putExtra("online", online);
		sendBroadcast(intent);
	}
	
	

	/* Method and var for reconnect attemp start */
	private class NetworkChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(final Context context, final Intent intent) {
			setOnline(TYPE_NOT_CONNECTED != getConnectivityStatus(context));
		}

		public static final int TYPE_NOT_CONNECTED = 0;
		public static final int TYPE_WIFI = 1;
		public static final int TYPE_MOBILE = 2;

		public int getConnectivityStatus(Context context) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
			Log.d("NetworkChangeReceiver", "getConnectivityStatus status: "+ (null == activeNetwork ? "Unknown or not connected" : activeNetwork.getState()));
			
			if (null != activeNetwork && activeNetwork.isConnected()) {
				Log.d("NetworkChangeReceiver", "getConnectivityStatus type: " + activeNetwork.getType());
				if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
					return TYPE_WIFI;
				}

				if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
					return TYPE_MOBILE;
				}
			}
			return TYPE_NOT_CONNECTED;
		}
	}

	public static IntentFilter getNetworkChangeIntentFilter() {
		IntentFilter ncIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		ncIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		return ncIntentFilter;
	}

	private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
	/* Method and var for reconnect attemp start */
}