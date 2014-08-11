package com.totalmobile.risksapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

public class BaseActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		getSupportActionBar().setLogo(
				getResources().getDrawable(R.drawable.ic_empty));
	}
}
