package com.vinfotech.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.http.conn.util.InetAddressUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * Utility class to generate unique id
 * 
 * @author ramanands
 * 
 */
public class UniqueIdUtil {

	private static UniqueIdUtil uniqueIdUtil = null;

	public static UniqueIdUtil getInstance() {
		if (null == uniqueIdUtil) {
			uniqueIdUtil = new UniqueIdUtil();
		}
		return uniqueIdUtil;
	}

	public void deleteInstance() {
		uniqueIdUtil = null;
	}

	/**
	 * 
	 * For the vast majority of applications, the requirement is to identify a
	 * particular installation, not a physical device. Fortunately, doing so is
	 * straightforward.<br/>
	 * <br/>
	 * 
	 * There are many good reasons for avoiding the attempt to identify a
	 * particular device. For those who want to try, the best approach is
	 * probably the use of ANDROID_ID on anything reasonably modern, with some
	 * fallback heuristics for legacy devices.
	 * 
	 * @param context
	 *            the application context
	 * @return device id
	 */
	public synchronized String getInstallationId(Context context) {
		File installation = new File(context.getFilesDir(), "INSTALLATION");
		try {
			if (!installation.exists())
				writeInstallationFile(installation);
			return readInstallationFile(installation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String readInstallationFile(File installation) throws IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) randomAccessFile.length()];
		randomAccessFile.readFully(bytes);
		randomAccessFile.close();
		return new String(bytes);
	}

	private void writeInstallationFile(File installation) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		fileOutputStream.write(id.getBytes());
		fileOutputStream.close();
	}

	/**
	 * Return unique device id<br/>
	 * <br/>
	 * 
	 * Advantages of using WLAN MAC address as Device ID:<br/>
	 * <br/>
	 * 
	 * It is unique identifier for all type of devices (smart phones and
	 * tablets).<br/>
	 * It remains unique if the application is reinstalled<br/>
	 * <br/>
	 * 
	 * Disadvantages of using WLAN MAC address as Device ID:<br/>
	 * <br/>
	 * 
	 * If device doesn’t have wifi hardware then you get null MAC address, but
	 * generally it is seen that most of the Android devices have wifi hardware
	 * and there are hardly few devices in the market with no wifi hardware.
	 * 
	 * 
	 * @param context
	 *            the application context
	 * @return device id
	 * 
	 */
	public String getWLANMACAddress(Context context) {
		if (null == context) {
			return null;
		}

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getMacAddress();
	}

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param ipv4
	 *            true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	@SuppressLint("DefaultLocale")
	public static String getIPAddress(boolean useIPv4) {
		try {
			List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface networkInterface : networkInterfaces) {
				List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
				for (InetAddress inetAddress : inetAddresses) {
					if (!inetAddress.isLoopbackAddress()) {
						String sAddr = inetAddress.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							if (!isIPv4) {
								// drop ip6 port suffix
								int delim = sAddr.indexOf('%');
								return delim < 0 ? sAddr : sAddr.substring(0, delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * Return unique device id<br/>
	 * <br/>
	 * 
	 * Advantages of using Bluetooth device address as Device ID:<br/>
	 * <br/>
	 * It is unique identifier for all type of devices (smart phones and
	 * tablets). There is generally a single Bluetooth hardware in all devices
	 * and it doesn’t gets changed.
	 * 
	 * Disadvantages of using Bluetooth device address as Device ID:<br/>
	 * <br/>
	 * If device hasn’t bluetooth hardware then you get null.
	 * 
	 * 
	 * @param context
	 *            the application context
	 * @return device id
	 * 
	 */
	public String getBluetoothAddress(Context context) {
		if (null == context) {
			return null;
		}

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		return bluetoothAdapter.getAddress();
	}

	/**
	 * Return unique device id<br/>
	 * <br/>
	 * 
	 * Advantages of using IMEI as Device ID:<br/>
	 * <br/>
	 * 
	 * The IMEI is unique for each and every device.<br/>
	 * It remains unique for the device even if the application is re-installed
	 * or if the device is rooted or factory reset.<br/>
	 * <br/>
	 * 
	 * Disadvantages of using IMEI as Device ID:<br/>
	 * <br/>
	 * 
	 * IMEI is dependent on the Simcard slot of the device, so it is not
	 * possible to get the IMEI for the devices that do not use Simcard.<br/>
	 * In Dual sim devices, we get 2 different IMEIs for the same device as it
	 * has 2 slots for simcard.
	 * 
	 * @param context
	 *            the application context
	 * @return device id
	 * 
	 */
	public String getIMEI(Context context) {
		if (null == context) {
			return null;
		}

		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * Return unique device id<br/>
	 * <br/>
	 * 
	 * Advantages of using Android_ID as Device ID:<br/>
	 * <br/>
	 * 
	 * It is unique identifier for all type of devices (smart phones and
	 * tablets).<br/>
	 * No need of any permission.<br/>
	 * It will remain unique in all the devices and it works on phones without
	 * Simcard slot.<br/>
	 * <br/>
	 * 
	 * Disadvantages of using Android_ID as Device ID:<br/>
	 * <br/>
	 * 
	 * If Android OS version is upgraded by the user then this may get changed.<br/>
	 * The ID gets changed if device is rooted or factory reset is done on the
	 * device.<br/>
	 * Also there is a known problem with a Chinese manufacturer of android
	 * device that some devices have same Android_ID.
	 * 
	 * @param context
	 *            the application context
	 * @return device id
	 * 
	 */
	public String getAndroidId(Context context) {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
}
