package com.totalmobile.risksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.viewpagerindicator.CirclePageIndicator;

public class ViewPagerActivity extends BaseActivity {

	private CirclePageIndicator circlePageIndicator;
	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private int[] imageResources = new int[] { R.drawable.page_1,
			R.drawable.page_2, R.drawable.page_3, R.drawable.page_4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);

		getSupportActionBar().setTitle(
				getResources().getString(R.string.app_name));

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);

		adapter = new ViewPagerAdapter(getSupportFragmentManager(),
				imageResources);
		viewPager.setAdapter(adapter);
		circlePageIndicator.setViewPager(viewPager);
		circlePageIndicator.setOnPageChangeListener(new PageChangeListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_map) {
			Intent mapIntent = new Intent(ViewPagerActivity.this, MapActivity.class);
			startActivity(mapIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class PageChangeListener implements ViewPager.OnPageChangeListener {

		private int currentPage = 0;

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				int adapterSize = adapter.getCount();
				if (currentPage == adapterSize - 1)
					getSupportActionBar().setTitle(
							getResources().getString(R.string.support));
				else
					getSupportActionBar().setTitle(
							getResources().getString(R.string.app_name));
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			this.currentPage = position;
		}

	}
}
