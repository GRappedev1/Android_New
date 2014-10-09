package com.vinfotech.utility;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Base64;

public class ImageUtil {

	/**
	 * Convert Square to Circle Image
	 * 
	 * @param bitmap
	 *            the bitmap to be circled
	 * @return the bitmap
	 */
	public static Bitmap getCircledBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static String encodeTobase64(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
		byte[] bytes = byteArrayOutputStream.toByteArray();

		String bitmapString = Base64.encodeToString(bytes, Base64.DEFAULT);

		return bitmapString;
	}

	public static Bitmap decodeBase64(String bitmapString) {
		if (null == bitmapString) {
			return null;
		}

		byte[] bytes = Base64.decode(bitmapString, Base64.DEFAULT);

		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

}
