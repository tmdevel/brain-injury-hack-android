package com.totalmobile.risksapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LeafletListAdapter extends ArrayAdapter<Leaflet> {

	private ArrayList<Leaflet> leaflets;
	private int resource;
	private Context context;

	public LeafletListAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<Leaflet> leaflets) {
		super(context, resource, textViewResourceId, leaflets);
		this.leaflets = leaflets;
		this.resource = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);

			holder = new ViewHolder();
			holder.leafletColorAndNumber = (TextView) convertView
					.findViewById(R.id.leafletColorAndNumber);
			holder.leafletTitle = (TextView) convertView
					.findViewById(R.id.leafletTitle);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		Leaflet leaflet = leaflets.get(position);
		holder.leafletTitle.setText(leaflet.getTitle());
		holder.leafletColorAndNumber.setText(String.valueOf(position + 1));
		holder.leafletColorAndNumber.setBackgroundColor(leaflet.getColor());

		return convertView;
	}

	class ViewHolder {
		TextView leafletColorAndNumber;
		TextView leafletTitle;
	}
}
