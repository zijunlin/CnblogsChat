package com.cnblogs.keyindex;

import com.cnblogs.keyindex.adapter.SectionAdapter;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.Section;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CnblogsActivity extends Activity implements OnItemClickListener {

	private GridView grdFucntion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cnblogs);
		grdFucntion = (GridView) findViewById(R.id.grdFucntion);
		grdFucntion.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		CnblogsIngContext context = CnblogsIngContext.getContext();
		SectionAdapter adapter = new SectionAdapter(this,
				context.getAllSection());
		grdFucntion.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Section model = (Section) parent.getItemAtPosition(position);
		Intent intent = new Intent(model.getAction());
		startActivity(intent);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			exitApp();
		}
		return super.dispatchKeyEvent(event);
	}

	public void exitApp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("您确定退出吗？").setTitle("退出")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
