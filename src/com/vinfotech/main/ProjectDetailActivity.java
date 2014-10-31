package com.vinfotech.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vinfotech.demoapp.R;
import com.vinfotech.model.ProjectBean;

/*
 * @author yogesh Tatwal 
 * 
 * this activity is showing detail of each project.
 */
public class ProjectDetailActivity extends Activity implements OnClickListener {
	private TextView projectTittle, platform, projectDetail;
	private ProjectBean bean;
	private ImageButton btnSlide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_project_detail_layout);

		Intent i = getIntent();
		bean = (ProjectBean) i.getSerializableExtra("object");

		init();
		setData();
		setListner();
	}

	private void setListner() {
		// TODO Auto-generated method stub
		btnSlide.setOnClickListener(this);
	}

	private void setData() {
		// TODO Auto-generated method stub
		if (bean != null) {
			projectTittle.setText(bean.project_name);
			platform.setText(bean.technology_used);
			projectDetail.setText(bean.projectonelinedescription);
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		projectTittle = (TextView) findViewById(R.id.header_name);
		platform = (TextView) findViewById(R.id.platform);
		projectDetail = (TextView) findViewById(R.id.project_detail);
		btnSlide = (ImageButton) findViewById(R.id.left_ib);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.left_ib:
			this.finish();
			break;

		default:
			break;
		}
	}
}
