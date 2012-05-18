package com.cnblogs.keyindex.chat.kernel;

import roboguice.config.AbstractAndroidModule;

import com.cnblogs.keyindex.chat.net.HttpSender;
import com.cnblogs.keyindex.chat.net.LoginHttpSender;
import com.cnblogs.keyindex.chat.net.ViewStateFormDownloader;
import com.cnblogs.keyindex.chat.net.Downloader;
import com.cnblogs.keyindex.chat.net.response.ProcessResponse;
import com.cnblogs.keyindex.chat.net.response.VsfProcessPesponse;

import com.cnblogs.keyindex.chat.service.ActivityCaller;
import com.cnblogs.keyindex.chat.service.Authenticator;
import com.cnblogs.keyindex.chat.service.Initialization;
import com.cnblogs.keyindex.chat.service.concrete.ConcreteActivityCaller;
import com.cnblogs.keyindex.chat.service.concrete.AppIniti;
import com.cnblogs.keyindex.chat.service.concrete.UserAuthenticator;
import com.google.inject.name.Names;

class CnblogsChatModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(Initialization.class).to(AppIniti.class);
		bind(ActivityCaller.class).to(ConcreteActivityCaller.class);
		bind(Downloader.class).to(ViewStateFormDownloader.class);
		bind(ProcessResponse.class).annotatedWith(
				Names.named("VsfProcessPesponse")).to(VsfProcessPesponse.class);
		bind(Authenticator.class).to(UserAuthenticator.class);

		bind(HttpSender.class).to(LoginHttpSender.class);

	}

}