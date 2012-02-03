package com.cnblogs.keyindex.kernel;

import com.cnblogs.keyindex.R;

class SectionRegister {

	public void onEnroll(CnblogsIngContext context) {
		context.enrollSection("µ«   ¬º", R.drawable.log_in,
				"com.cnblogs.keyindex.UserAcitivity.sigin");
		context.enrollSection("…¡   ¥Ê", R.drawable.message_star,
				"com.cnblogs.keyindex.FlashMessageActivity.view");
		context.enrollSection("∑¢ÀÕ…¡¥Ê", R.drawable.send_message,
		"com.cnblogs.keyindex.FlashMessageActivity.Sender");
	}

}
