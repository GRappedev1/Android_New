package com.vinfotech.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.vinfotech.demoapp.R;

/**
 * Receive a push message from the Google cloud messaging (GCM) service. This
 * class should be modified to include functionality specific to your
 * application. This class must have a no-arg constructor and pass the sender id
 * to the superclass constructor.
 * 
 * @author ramanands
 */

public class GCMIntentService extends GCMBaseIntentService {
	private static final String LOG_TAG = "GCMIntentService";
	private static int sNotificationId = 101;
	public static final String PROJECT_ID = "919986327244";
	private static GCMRegisterListener sGCMRegisterListener;

	/**
	 * Register the device for GCM.
	 */
	public static void register(Context mContext) {
		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_ID);
	}

	public GCMIntentService() {
		super(PROJECT_ID);
	}

	public static void addGCMRegisterListener(GCMRegisterListener listener) {
		GCMIntentService.sGCMRegisterListener = listener;
	}

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(LOG_TAG, "onError error id:" + errorId);
		if (null != sGCMRegisterListener) {
			sGCMRegisterListener.onErrored();
		}
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		for (String key : intent.getExtras().keySet()) {
			Log.d("GCMIntentService", "onMessage - key=" + key);
		}
		String message = null != intent.getExtras().getString("alert") ? intent.getExtras().getString("alert") : intent.getExtras()
				.getString("message");

		setNotification(message);
	}

	/**
	 * Called when a registration token has been received.
	 */
	@Override
	protected void onRegistered(Context context, String registration) {
		Log.i(LOG_TAG, "Registered Device Start:" + registration);
		if (null != sGCMRegisterListener) {
			sGCMRegisterListener.onRegistered(registration);
		}
	}

	/**
	 * Called when the device has been unregistered.
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(LOG_TAG, "unRegistered Device registrationId:" + registrationId);
	}

	/**
	 * Show notification in notification bar
	 * 
	 * @param message
	 *            message to be shon in notification bar
	 */
	private void setNotification(String message) {
		Intent intent = new Intent(this, LoginActivity.class);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getResources().getString(R.string.app_name)).setContentText(message);

		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(sNotificationId, mBuilder.build());
	}

	/**
	 * Interface to listen for registration process with the C2DM server
	 * 
	 * @author ramanands
	 * 
	 */
	public interface GCMRegisterListener {
		/**
		 * Registered successfully
		 * 
		 * @param deviceId
		 *            the device id return by the C2DM server on successful
		 *            registration
		 */
		void onRegistered(String deviceId);

		/**
		 * Error during registration process, may be re-init the process
		 */
		void onErrored();
	}
}