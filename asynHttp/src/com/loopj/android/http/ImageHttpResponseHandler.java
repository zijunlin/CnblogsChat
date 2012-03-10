package com.loopj.android.http;

/**
 * this class is to  downloader  byte  file;
 */
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;

import android.graphics.drawable.Drawable;
import android.os.Message;

public class ImageHttpResponseHandler extends AsyncHttpResponseHandler {

	private static final int IMAGE_SUCCESS_MESSAGE = 4;

	/**
	 * Fired when a request returns successfully and contains a byte Drawable
	 * object
	 * 
	 * @param response
	 *            the parsed Drawable object found in the server response (if
	 *            any)
	 */
	public void onSuccess(Drawable drawable) {
	}

	protected void handleSuccessMessage(Drawable drawable) {
		onSuccess(drawable);
	}

	protected void sendSuccessMessage(Drawable drawable) {
		sendMessage(obtainMessage(IMAGE_SUCCESS_MESSAGE, drawable));
	}

	@Override
	void sendResponseMessage(HttpResponse response) {
		StatusLine status = response.getStatusLine();
		Drawable drawable = null;
		try {
			HttpEntity entity = null;
			HttpEntity temp = response.getEntity();
			if (temp != null) {
				entity = new BufferedHttpEntity(temp);
			}
			
			drawable = Drawable.createFromStream(entity.getContent(),
					"header.png");

		} catch (IOException e) {
			sendFailureMessage(e, null);
		}

		if (status.getStatusCode() !=HttpStatus.SC_OK) {
			sendFailureMessage(new HttpResponseException(
					status.getStatusCode(), status.getReasonPhrase()), "error");
		} else {
			sendSuccessMessage(drawable);
		}

	}

	@Override
	protected void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (msg.what == IMAGE_SUCCESS_MESSAGE) {
			handleSuccessMessage((Drawable) msg.obj);
		}
	}

}
