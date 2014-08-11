package com.totalmobile.risksapp.tasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.totalmobile.risksapp.data.OrganisationDataSource;
import com.totalmobile.risksapp.entities.Organisation;

public class LoadOrganisations extends AsyncTask<Void, Void, List<Organisation>> {
	
	private Context context;
	private OnOrganisationsLoaded onOrganisationsLoaded;
	
	private int categoryId;
	private double currentLong;
	private double currentLat;
	private double radius;
	private String organisationName;
	

	public LoadOrganisations(Context context, OnOrganisationsLoaded onOrganisationsLoaded, int categoryId, double currentLong, 
			double currentLat, double radius, String organisationName) {
		this.context = context;
		this.onOrganisationsLoaded = onOrganisationsLoaded;
		this.categoryId = categoryId;
		this.currentLong = currentLong;
		this.currentLat = currentLat;
		this.radius = radius;
		this.organisationName = organisationName;
	}

	@Override
	protected List<Organisation> doInBackground(Void... params) {
		OrganisationDataSource organisationDataSource = new OrganisationDataSource(context);
		organisationDataSource.open();
		List<Organisation> organisations = 
				organisationDataSource.getOrganisations(categoryId, currentLong, currentLat, radius, organisationName);
		organisationDataSource.close();
		return organisations;
	}
	
	@Override
	protected void onPostExecute(List<Organisation> result) {
		super.onPostExecute(result);
		onOrganisationsLoaded.perform(result);
	}

}
