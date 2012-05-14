package com.cnblogs.keyindex.chat.net;

import com.cnblogs.keyindex.chat.R;
import com.cnblogs.keyindex.chat.model.ViewStateForms;
import com.cnblogs.keyindex.chat.net.response.ProcessResponse;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ViewStateFormDownloader implements Downloader {

	private AsyncHttpClient httpClient;

	private static final String EMPTY="";
	@Inject @Named("VsfProcessPesponse")
	private ProcessResponse handleResponse;

	public ViewStateFormDownloader() {
		httpClient = new AsyncHttpClient();
	}

	@Override
	public void download(String url, Handler handler) {
		getViewStateForms(url, handler);
	}

	/**
	 * download the viewstate ,which the login need;
	 * 
	 * @param handler
	 */
	private void getViewStateForms(String url, final Handler handler) {

		httpClient.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				handler.sendEmptyMessage(R.string.msgStartDownload);
			}

			@Override
			public void onSuccess(String content) {
				try {
					ViewStateForms model= (ViewStateForms) handleResponse.processDownloaded(content);
					if(model != null && model.getViewState() != EMPTY)
					{
						Message msg = new Message();
						msg.what = R.string.msgSuccessDowloadedStateForm;
						msg.obj = model;
						handler.sendMessage(msg);
					}
					else
					{
						handler.sendEmptyMessage(R.string.msgFailureDowloadedStateForm);
					}
				} catch (Exception ex) {
					handler.sendEmptyMessage(R.string.msgFailureDowloadedStateForm);
					// Log e 会将将异常继续抛出
					Log.e("cnblogs", ex.getMessage());

				}

			}

			@Override
			public void onFailure(Throwable error, String content) {
				handler.sendEmptyMessage(R.string.msgFailureDowloadedStateForm);
			}

		});

	}

	public void setHttpClient(AsyncHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setHandleResponse(ProcessResponse handleResponse) {
		this.handleResponse = handleResponse;
	}

}
