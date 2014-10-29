package com.vinfotech.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.vinfotech.demoapp.R;
import com.vinfotech.server.mime.HttpMultipartMode;
import com.vinfotech.server.mime.MultipartEntity;
import com.vinfotech.server.mime.content.FileBody;
import com.vinfotech.server.mime.content.StringBody;
import com.vinfotech.utility.DialogUtil;

@SuppressLint("DefaultLocale")
public class HttpConnector {
	private static final String ACCEPT = "application/json, api_version=1";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String USER_AGENT = "Medlinx-android";

	private Context context;
	private HttpResponseListener listener;
	private Dialog dialog = null;
	private Handler handler = null;
	private HttpClient httpClient = null;
	private HttpResponse httpResponse = null;
	private String accessToken = null;
	private Runnable runnable = null;

	public HttpConnector(Context context, HttpResponseListener listener, Dialog dialog) {
		this.context = context;
		this.listener = listener;
		this.dialog = dialog;
		this.handler = new Handler();
		this.httpClient = new DefaultHttpClient();

		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public void setHttpResponseListener(HttpResponseListener listener) {
		this.listener = listener;

	}

	public interface HttpResponseListener {
		void onResponse(int reqCode, int statusCode, String json);

		void onCancel(boolean canceled);
	}

	public void executeAsync(final String url, final int reqCode, final boolean isGet, boolean runInBg, int urlType) {
		String method;

		if (isGet) {
			method = "get";
		} else {
			method = "post";
		}

		executeAsync(url, reqCode, method, runInBg, null, null, urlType);
	}

	public void executeAsync(final String remainUrl, final int reqCode, final String method, boolean runInBg, final String jsonData,
			final ArrayList<BasicNameValuePair> extraFormData, int urlType) {
	/*	Log.i("HttpConnector", "executeAsync - url=" + remainUrl + ", reqCode=" + reqCode + ", method=" + method + ", runInBg=" + runInBg
				+ ", jsonData=" + jsonData + ", extraFormData=" + extraFormData);
//*/		//Log.i(jsonData, jsonData);
		
		if (TYPE_NOT_CONNECTED == getConnectivityStatus(context)) {
			
			DialogUtil.showOkDialog(context,R.string.Network_erro, R.string.No_internet_connection);
			//showOkDialog(context, "Network Error", "Can not reach to SubAssistant servers!");
						return;
		}

		 String url = "";

		switch (urlType) {
			case HTTPRequest.URLTYPE_SERVICE:
				url = HTTPRequest.SERVICE_URL + remainUrl;
				break;
	
			case HTTPRequest.URLTYPE_IMAGE:
				url = HTTPRequest.IMAGE_URL + remainUrl;
	
				break;
	
			case HTTPRequest.URLTYPE_UPLOAD:
				url = HTTPRequest.UPLOAD_URL + remainUrl;
	
				break;
			case HTTPRequest.URLTYPE_EXTERNAL:
				url = remainUrl;
				break;
			default:
				url = HTTPRequest.SERVICE_URL + remainUrl;
				break;
		}

		if (!runInBg && null != dialog && !dialog.isShowing()) {
			dialog.show();
		}

		final String finalUrl = url;
		
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					// Request body
					if (method.toLowerCase().contains("get")) {
						
						HttpGet httpGet = new HttpGet(finalUrl);
						if (null != accessToken) {
							httpGet.addHeader("Authorization", "OAuth " + accessToken);
						}

						httpGet.addHeader("Accept", ACCEPT);
						httpGet.addHeader("Content-Type", CONTENT_TYPE);
						httpGet.addHeader("User-Agent", USER_AGENT);

						httpResponse = httpClient.execute(httpGet);
					} else if (method.toLowerCase().contains("post")) {
						HttpPost httpPost = new HttpPost(finalUrl);

						if (accessToken != null && extraFormData == null) {
							httpPost.addHeader("Authorization", "OAuth " + accessToken);
						}
						// Used for posting an image to AWS
						if (extraFormData != null) {
							MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
							for (int i = 0; i < extraFormData.size(); i++) {
								BasicNameValuePair pair = extraFormData.get(i);
								if (pair.getName().equalsIgnoreCase("file")) {
									String path = pair.getValue();
									int lastSlash = path.lastIndexOf("/");
									String filename = path.substring(lastSlash + 1);

									FileBody fb = new FileBody(new File(pair.getValue()), "multipart/form-data", filename);
									entity.addPart(pair.getName(), fb);
								} else
									entity.addPart(pair.getName(), new StringBody(pair.getValue()));
							}
							httpPost.setEntity(entity);
						} else {
							// Don't add headers for multi-form post or else it
							// breaks!
							httpPost.addHeader("Accept", ACCEPT);
							httpPost.addHeader("Content-Type", CONTENT_TYPE);
							httpPost.addHeader("User-Agent", USER_AGENT);
						}

						if (jsonData != null) {
							httpPost.setEntity(new StringEntity(jsonData));
						}

						httpResponse = httpClient.execute(httpPost);
					} else if (method.toLowerCase().contains("put")) {
						HttpPut httpPut = new HttpPut(finalUrl);

						if (null != accessToken) {
							httpPut.addHeader("Authorization", "OAuth " + accessToken);
						}

						if (jsonData != null) {
							httpPut.setEntity(new StringEntity(jsonData));
						}

						httpPut.addHeader("Accept", ACCEPT);
						httpPut.addHeader("Content-Type", CONTENT_TYPE);
						httpPut.addHeader("User-Agent", USER_AGENT);

						httpResponse = httpClient.execute(httpPut);
					} else if (method.contains("delete")) {
						HttpDelete httpDelete = new HttpDelete(finalUrl);

						if (null != accessToken) {
							httpDelete.addHeader("Authorization", "OAuth " + accessToken);
						}

						httpDelete.addHeader("Accept", ACCEPT);
						httpDelete.addHeader("Content-Type", CONTENT_TYPE);
						httpDelete.addHeader("User-Agent", USER_AGENT);

						httpResponse = httpClient.execute(httpDelete);
					}

					// Response
					HttpEntity entity = httpResponse.getEntity();
					StatusLine sl = httpResponse.getStatusLine();
					final int statusCode = sl.getStatusCode();

					InputStream contentStream = null;
					try {
						contentStream = entity.getContent();
					} catch (Exception e) {
					}

					// Read Response
					try {
						final String response = readStreamFully(contentStream);
						logFullResponse(response);
						if (null != listener) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									listener.onResponse(reqCode, statusCode, response);
								}
							});
						}
					} catch (Exception e) {
						if (null != listener) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									listener.onResponse(reqCode, statusCode, null);
								}
							});
						}
					}

				}catch(UnknownHostException e){
					DialogUtil.showOkDialog(context, "Unable to connect the server");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					

					if (null != listener) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								listener.onResponse(reqCode, 0, null);
							}
						});
					}
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (null != dialog && dialog.isShowing()) {
							dialog.dismiss();
						}
					}
				});
			}
		};

		new Thread(runnable).start();
	}

	private String readStreamFully(InputStream inputStream) {
		if (inputStream == null) {
			System.out.println("inputStream null, returning blank");
			return "";
		}

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();

		String strCurrentLine = null;
		try {
			/* Read until all response is read */
			while ((strCurrentLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(strCurrentLine + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

	public void logFullResponse(String response) {
		final int chunkSize = 4000;
		if (null != response && response.length() > chunkSize) {
			int chunks = (int) Math.ceil((double) response.length() / (double) chunkSize);
			for (int i = 1; i <= chunks; i++) {
				if (i != chunks) {
					Log.i("HttpConnector", "logResponse - response=" + response.substring((i - 1) * chunkSize, i * chunkSize));
				} else {
					Log.i("HttpConnector", "logResponse - response=" + response.substring((i - 1) * chunkSize, response.length()));
				}
			}
		} else {
			Log.i("HttpConnector", "logResponse - response=" + response);
		}
	}

	public static final int TYPE_NOT_CONNECTED = 0;
	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;

	public int getConnectivityStatus(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		Log.d("Helper", "getConnectivityStatus status: " + (null == activeNetwork ? "Unknown or not connected" : activeNetwork.getState()));
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

	/*public static void showOkDialog(Context context, String title, String message) {
		if (null == context || null == message) {
			return;
		}
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if(null != title){
				builder.setTitle(title);
			}
			builder.setMessage(message);
			builder.setCancelable(true);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}*/

}
