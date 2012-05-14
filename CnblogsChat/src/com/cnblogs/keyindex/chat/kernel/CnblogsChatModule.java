package com.cnblogs.keyindex.chat.kernel;

import roboguice.config.AbstractAndroidModule;

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

	}

	// private WeakHashMap<String, String> classNames = new WeakHashMap<String,
	// String>();
	//
	// /**
	// * 此方法用于移除 对距离类型的依赖（引用依赖）。
	// * 造成缺点是 ：没有了强类型的优势，如： 在编译时不能做类型检查，因此需要保证 参数的正确,或者重命名是不会被eclipse跟踪
	// *
	// * @param name
	// * @return
	// * @throws ClassNotFoundException
	// */
	// @SuppressWarnings("rawtypes")
	// private Class getClassType(String name) throws ClassNotFoundException {
	// String className=classNames.get(name);
	// Class c = Class.forName(className);
	//
	// return c;
	// }
	//
	//
	// protected void addClass()
	// {
	//
	// // classNames.put("IAuthenticator", "")
	// }
	//

}