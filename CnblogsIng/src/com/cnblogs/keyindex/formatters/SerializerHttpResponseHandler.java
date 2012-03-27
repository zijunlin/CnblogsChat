package com.cnblogs.keyindex.formatters;

import android.os.Message;
import android.util.Log;


import com.cnblogs.indexkey.formatter.FormatterFactory;
import com.cnblogs.indexkey.formatter.IFormatter;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SerializerHttpResponseHandler extends AsyncHttpResponseHandler {

	private int SERIALIZER_SUCCESS_MESSAGE = 5;

	private String serializerClassName;

	@Override
	protected void sendSuccessMessage(String responseBody) {

		try {
			IFormatter format = FormatterFactory
					.getFormatter(serializerClassName);
			Object body = format.format(responseBody);
			sendMessage(obtainMessage(SERIALIZER_SUCCESS_MESSAGE, body));
		} catch (Exception e) {
			Log.e("Cnblogs", "serializer error");
		}

	}

	public void setSerializer(String className) {
		serializerClassName = className;
	}

	@Override
	protected void handleMessage(Message msg) {
		super.handleMessage(msg);
		if(msg.what==SERIALIZER_SUCCESS_MESSAGE)
		{
			handleSuccessMessage(msg.obj);
		}
	}

	private void handleSuccessMessage(Object obj) {
		onSerializerSuccess(obj);
		
	}

	public void onSerializerSuccess(Object obj) {
		
		
	}
	
	
	

}
