package com.totalmobile.risksapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private int[] imageResources;
	private int ADAPTER_SIZE = 0;

	EmptyFragment[] fragments = new EmptyFragment[8];

	public ViewPagerAdapter(FragmentManager fm, int[] imageResources) {
		super(fm);

		this.imageResources = imageResources;
		ADAPTER_SIZE = imageResources.length + 1;

		for (int i = 0; i < ADAPTER_SIZE; i++) {
			Bundle args = new Bundle();
			args.putString("textView", i + "");
			EmptyFragment fragment = new EmptyFragment();
			fragment.setArguments(args);
			fragments[i] = fragment;
		}
	}

	@Override
	public Fragment getItem(int position) {
		if (position == ADAPTER_SIZE - 1) {
			return new LeafletListFragment();
		} else {
			// return image fragment
			return fragments[position];
		}
	}

	@Override
	public int getCount() {
		return ADAPTER_SIZE;
	}

}
