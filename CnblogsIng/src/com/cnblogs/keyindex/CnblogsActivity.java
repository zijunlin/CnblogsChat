package com.cnblogs.keyindex;


import com.cnblogs.keyindex.adapter.SectionAdapter;
import com.cnblogs.keyindex.kernel.CnblogsIngContext;
import com.cnblogs.keyindex.model.Section;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

}
