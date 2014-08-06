package com.totalmobile.risksapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class LeafletListFragment extends ListFragment {

	private ArrayList<Leaflet> leaflets;
	private LeafletListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_leaflet_list, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String[] leafletTitles = getActivity().getResources().getStringArray(
				R.array.leaflet_titles);
		int[] leafletColors = getActivity().getResources().getIntArray(
				R.array.leaflet_colors);

		leaflets = new ArrayList<Leaflet>();
		int i = 0;
		int size = leafletTitles.length;

		for (i = 0; i < size; i++) {
			Leaflet leaflet = new Leaflet(leafletTitles[i], leafletColors[i], 0);
			leaflets.add(leaflet);
		}

		adapter = new LeafletListAdapter(getActivity(), R.layout.row_leaflet,
				R.id.leafletTitle, leaflets);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Leaflet leaflet = adapter.getItem(position);
		Toast.makeText(getActivity(), leaflet.getTitle(), Toast.LENGTH_LONG)
				.show();
	}

}
