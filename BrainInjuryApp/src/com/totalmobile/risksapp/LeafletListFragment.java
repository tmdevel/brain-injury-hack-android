package com.totalmobile.risksapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.Toast;

public class LeafletListFragment extends ListFragment {

	private static Context context = null;
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

		context = getActivity();
		
		String[] leafletTitles = getActivity().getResources().getStringArray(
				R.array.leaflet_titles);
		int[] leafletColors = getActivity().getResources().getIntArray(
				R.array.leaflet_colors);

		leaflets = new ArrayList<Leaflet>();
		int i = 0;
		int size = leafletTitles.length;

		for (i = 0; i < size; i++) {
			Leaflet leaflet = new Leaflet(leafletTitles[i], leafletColors[i], null);
			leaflets.add(leaflet);
		}

		adapter = new LeafletListAdapter(getActivity(), R.layout.row_leaflet,
				R.id.leafletTitle, leaflets);
		setListAdapter(adapter);
	}
	
	public Uri getUri(String s, Context c){
		
		Uri URI = FileManager.CopyReadAssets(s, c);
		
		return URI;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Leaflet leaflet = adapter.getItem(position);
		
		Uri URI = getUri(leaflet.getTitle(), context);	
		
		Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		pdfIntent.setDataAndType(URI, "application/pdf");
		
		try{
			startActivity(pdfIntent);
		}
		catch (ActivityNotFoundException e){
			startActivity(Intent.createChooser(pdfIntent, "Open PDF with..."));
		}
		
	}

}
