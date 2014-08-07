package com.totalmobile.risksapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private int[] imageResources;
	private int ADAPTER_SIZE = 0;

	public ViewPagerAdapter(FragmentManager fm, int[] imageResources) {
		super(fm);

		this.imageResources = imageResources;
		ADAPTER_SIZE = imageResources.length + 1;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == ADAPTER_SIZE - 1) {
			return new LeafletListFragment();
		} else {
			return new LeafletListFragment();
//			return ImageFragment.newInstance(imageResources[position]);
		}
	}

	@Override
	public int getCount() {
		return ADAPTER_SIZE;
	}

}
