package com.cnblogs.keyindex.kernel;

import com.cnblogs.keyindex.R;

class SectionRegister {

	public void onEnroll(CnblogsIngContext context) {
		context.enrollSection("��   ¼", R.drawable.log_in,
				"com.cnblogs.keyindex.UserAcitivity.sigin");
		context.enrollSection("��   ��", R.drawable.message_star,
				"com.cnblogs.keyindex.FlashMessageActivity.view");
	}

}
