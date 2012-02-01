package com.cnblogs.keyindex.service;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import com.cnblogs.keyindex.R;
import com.cnblogs.keyindex.StartActivity;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.res.AspDotNetForms;

public class InitService implements Callback {

	private StartActivity activity;
	private Handler startHandler;
	private String uri;
	private int delayMillis;
	private ResourceExplorer re;

	public InitService(StartActivity context) {
		activity = context;
		startHandler = new Handler(this);
		Resources res = activity.getResources();
		uri = res.getString(R.string.urlPassport);
		delayMillis = res.getInteger(R.integer.delayMillis);
	}

	public void startInit() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				initCnblogsContext();
				bulidAspForms();

			}
		}).start();
	}

	private void initCnblogsContext() {
		startHandler.sendEmptyMessage(R.string.msgStart);
		CnblogsIngContext context = CnblogsIngContext.getContext();
		context.initContext();
	}

	private void bulidAspForms() {
		startHandler.sendEmptyMessage(R.string.msgDownload);
		re = new ResourceExplorer();
		re.getResource(uri).serializerResult(AspDotNetForms.class.getName());
		startHandler.sendEmptyMessage(R.string.msgInitContext);
		AspDotNetForms model = (AspDotNetForms) re.getResponseResource();
		if (model != null && model.getViewState() != null) {
			startHandler.sendEmptyMessage(R.string.msgSuccessStart);
			CnblogsIngContext.getContext().setAspDotNetForms(model);
		} else {
			startHandler.sendEmptyMessage(R.string.msgInitError);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		activity.onMessageChanged(msg.what);
		switch (msg.what) {
		case R.string.msgSuccessStart:
			completeInit();
			break;
		case R.string.msgInitError:
			error();
			break;
		default:
		}
		return false;
	}

	private void completeInit() {
		startHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				activity.onStartCatalog();
			}
		}, delayMillis);
	}

	private void error() {
		activity.onError();
		startHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				bulidAspForms();
			}
		}, 5000);
	}
}
