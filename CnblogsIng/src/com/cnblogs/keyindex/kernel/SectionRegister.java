package com.cnblogs.keyindex.kernel;

import com.cnblogs.keyindex.R;

class SectionRegister {

	private String loginSectionTitle = "µÇ   Â¼";
	private String loginAction = "com.cnblogs.keyindex.UserAcitivity.sigin";

	private String ingSectionTitle = "ÉÁ   ´æ";
	private String ingAction = "com.cnblogs.keyindex.FlashMessageActivity.view";

	private String senderTitle = "·¢ËÍ";
	private String senderAction = "com.cnblogs.keyindex.FlashMessageActivity.Sender";

	public void onEnroll(CnblogsIngContext context) {
		context.enrollSection(loginSectionTitle, R.drawable.log_in, loginAction);
		context.enrollSection(ingSectionTitle, R.drawable.message_star,
				ingAction);
		context.enrollSection(senderTitle, R.drawable.send_message,
				senderAction);
	}

}
