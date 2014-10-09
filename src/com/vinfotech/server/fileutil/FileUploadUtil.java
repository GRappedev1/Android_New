package com.vinfotech.server.fileutil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * Class to show upload file popup. Basic idea is to hold all buttons initially
 * in invisible mode and then make them visible upon need.
 * 
 * @author ramanands
 * 
 */
public class FileUploadUtil {
	// caller context
	private Context baseContext;
	// caller activity for reference and showing success/error messages,
	// starting new activities
	private Activity baseActivity;

	// id for capture from camera
	public static final int BUTTON_CAMERA_ROLL = 0;
	// id for select image from device
	public static final int BUTTON_IMAGE = 1;
	// id for select audio from device
	public static final int BUTTON_AUDIO = 2;
	// id for select video from device
	public static final int BUTTON_VIDEO = 3;

	/**
	 * FileUploadUtil constructor
	 * 
	 * @param context
	 *            the caller context
	 */
	public FileUploadUtil(Context context) {
		baseContext = context;
		baseActivity = (Activity) context;
	}

	// re-init variables for local usage
	private static final int MEDIA_TYPE_IMAGE = 0;
	private static final int MEDIA_TYPE_AUDIO = 2;

	/**
	 * Launches the phone's gallery for file selection
	 * 
	 * @param mediaType
	 *            the type identifier of media i.e. photo, audio, or video
	 * @param requestCode
	 *            the request code to pass that will be return with selection
	 * @param modeSingleSelection
	 *            select single or multiple files
	 */
	public void openGalleryForFile(int mediaType, int requestCode, boolean modeSingleSelection) {
		if (modeSingleSelection) {
			Intent intent = new Intent();
			String type = (MEDIA_TYPE_IMAGE == mediaType ? "image" : (MEDIA_TYPE_AUDIO == mediaType ? "audio" : "video")) + "/*";
			Log.d(this.getClass().getName(), "type: " + type);
			intent.setType(type);
			intent.setAction(Intent.ACTION_GET_CONTENT);
			baseActivity.startActivityForResult(Intent.createChooser(intent, "Select File to Upload"), requestCode);
		} else {
			// Intent galleryintent = new Intent(baseContext,
			// CustomGallery.class);
			// galleryintent.putExtra(CustomGallery.MEDIA_TYPE, mediaType);
			// baseActivity.startActivityForResult(galleryintent,
			// FileUploadUtil.UPLOAD_FILE);
		}
	}

	// request codes
	public static final int UPLOAD_FILE = 1000;
	public static final int CAPTURE_FILE_FROM_CAMERA = 1001;
	public static final int CROP_IMAGE_FROM_URI = 1002;

	/**
	 * Converts Uri to equal absolute path
	 * 
	 * @param uri
	 *            which absolute path is required
	 * @return corresponding absolute path of uri
	 */
	@SuppressWarnings("deprecation")
	public String getAbsolutePath(Uri uri) {
		String[] projectionStringArray = { MediaStore.Images.Media.DATA };
		Cursor cursor = baseActivity.managedQuery(uri, projectionStringArray, null, null, null);
		int columnIndexInt = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(columnIndexInt);
	}

	/**
	 * Return byte array of file
	 * 
	 * @param uri
	 *            the Uri of files whose content needs to returned
	 * @return byte array of the file if file found null otherwise
	 */
	public byte[] getFileContent(Uri uri) {
		try {
			// try to read file in byte array
			return readFile(uri);
		} catch (Exception e) {
			Log.d(this.getClass().getName(), "Unable to read file content, message: " + e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * Read file in byte array
	 * 
	 * @param uri
	 *            Uri fo the file
	 * @return byte array
	 * @throws IOException
	 *             the exception to be thrown if file not found
	 */
	private byte[] readFile(Uri uri) throws IOException {
		// init file
		File file = new File(getAbsolutePath(uri));
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		try {
			long longlength = randomAccessFile.length();
			int length = (int) longlength;
			if (length != longlength)
				throw new IOException("File size >= 2 GB");

			// Read file and return data
			byte[] data = new byte[length];
			randomAccessFile.readFully(data);
			return data;
		} finally {
			randomAccessFile.close();
		}
	}

	/**
	 * Launch capture photo screen
	 * 
	 * @param actionCode
	 *            the request code to be return to caller activity
	 */
	public void dispatchTakePictureIntent(int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		captureImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_image_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, captureImageUri);
		takePictureIntent.putExtra("return-data", true);
		baseActivity.startActivityForResult(takePictureIntent, actionCode);
	}

	/**
	 * Uri that will hold the path of captured image
	 */
	private Uri captureImageUri;

	/**
	 * Return the Uri of captured image
	 * 
	 * @return the Uri of captured image
	 */
	public Uri getCaptureImageUri() {
		return captureImageUri;
	}

	/**
	 * Utility method to convert bitmap to byte array
	 * 
	 * @param bitmap
	 *            the bitmap to convert
	 * @return null if no bitmap is passed otherwise byte array of the bitmap
	 */
	public byte[] getByteArrayFromBitmat(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	/**
	 * Starts cropping utility screen
	 * 
	 * @param captureUri
	 *            the captured image uri
	 */
	public void startCropImageActivity(Uri captureUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = baseActivity.getPackageManager().queryIntentActivities(intent, 0);

		// check whether utility exists or not
		if (list.size() == 0) {
			// utility doesn't exist show the message to the user
			Toast.makeText(baseContext, "Can not find image crop app", Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(captureUri);
			// width of selection rectangle
			intent.putExtra("outputX", 200);
			// height of selection rectangle
			intent.putExtra("outputY", 200);
			// aspect ration
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// allow scalling of the rectangle
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			// Utility exists start it
			if (list.size() >= 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);
				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
				baseActivity.startActivityForResult(i, CROP_IMAGE_FROM_URI);
			}
		}
	}

}
