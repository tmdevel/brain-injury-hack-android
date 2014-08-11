package com.totalmobile.risksapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

public class ViewPagerActivity extends FragmentActivity {

	private CirclePageIndicator circlePageIndicator;
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private int[] imageResources = new int[] { R.drawable.page_1,
			R.drawable.page_2, R.drawable.page_3, R.drawable.page_4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);

		adapter = new ViewPagerAdapter(getSupportFragmentManager(),
				imageResources);
		viewPager.setAdapter(adapter);
		circlePageIndicator.setViewPager(viewPager);
	}
	
}
