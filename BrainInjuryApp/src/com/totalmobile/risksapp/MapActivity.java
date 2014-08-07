package com.totalmobile.risksapp;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.totalmobile.risksapp.entities.Constants;
import com.totalmobile.risksapp.entities.Organisation;
import com.totalmobile.risksapp.tasks.LoadOrganisations;
import com.totalmobile.risksapp.tasks.OnOrganisationsLoaded;
import com.totalmobile.risksapp.tasks.OnWaitForLocationFinished;
import com.totalmobile.risksapp.tasks.WaitForLocation;

public class MapActivity extends BaseActivity {

	private GoogleMap map;

	private boolean locationLoaded = false;
	private WaitForLocation waitForLocation;
	private LocationManager locationManager;

	private double currentLong;
	private double currentLat;

	private List<Organisation> currentOrganisations;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_map);

		getSupportActionBar().setTitle(
				getResources().getString(R.string.map_activity_title));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SupportMapFragment supportFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.supportMapFragment);
		map = supportFragment.getMap();

		LatLng currentPoint = new LatLng(Constants.DEFAULT_LOCATION_LAT,
				Constants.DEFAULT_LOCATION_LONG);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPoint, 10));
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationLoaded = false;
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		final Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (!locationLoaded) {
					locationChanged(location);
					waitForLocation.cancel(true);
				}
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

		};

		waitForLocation = new WaitForLocation(onWaitForLocationFinished);

		String locationProvider = locationManager.getBestProvider(criteria,
				true);
		if (locationProvider == null) {
			loadLastLoaction();
			return;
		}

		setProgressBarIndeterminateVisibility(true);
		locationManager.requestLocationUpdates(
				locationManager.getBestProvider(criteria, true), 0, 0,
				locationListener);
		waitForLocation.execute(10000l);
	}

	private OnWaitForLocationFinished onWaitForLocationFinished = new OnWaitForLocationFinished() {

		@Override
		public void perform() {
			setProgressBarIndeterminateVisibility(false);
			if (!locationLoaded) {
				loadLastLoaction();
				return;
			}

		}
	};

	private void locationChanged(Location location) {
		currentLong = location.getLongitude();
		currentLat = location.getLatitude();
		locationLoaded = true;
		loadOrganisations();
	}

	private void loadLastLoaction() {

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		Location lastLocation = locationManager
				.getLastKnownLocation(locationManager.getBestProvider(criteria,
						false));
		String text;

		if (lastLocation == null) {
			text = String.format(
					getResources().getString(
							R.string.location_services_not_found),
					getResources().getText(R.string.default_location));
			currentLong = Constants.DEFAULT_LOCATION_LONG;
			currentLat = Constants.DEFAULT_LOCATION_LAT;

			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		} else {
			currentLong = lastLocation.getLongitude();
			currentLat = lastLocation.getLatitude();
		}
		loadOrganisations();
	}

	private void loadOrganisations() {
		LoadOrganisations loadOrganisations = new LoadOrganisations(this,
				onOrganisationsLoaded, 1, currentLong, currentLat, 10.0f, "");
		loadOrganisations.execute();
	}

	private OnOrganisationsLoaded onOrganisationsLoaded = new OnOrganisationsLoaded() {

		@Override
		public void perform(List<Organisation> organisations) {
			currentOrganisations = organisations;
			if (organisations == null || organisations.size() == 0) {
				Toast.makeText(MapActivity.this,
						getString(R.string.no_data_found), Toast.LENGTH_LONG)
						.show();
				return;
			}
			showOnMap();
		}
	};

	private void showOnMap() {
		map.clear();

		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_action_place);
		for (Organisation organisation : currentOrganisations) {
			LatLng point = new LatLng(organisation.getLatitude() / 1E6,
					organisation.getLongitude() / 1E6);
			map.addMarker(new MarkerOptions().position(point).icon(icon)
					.title(organisation.getName()));
		}

		LatLng currentPoint = new LatLng(currentLat, currentLong);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPoint, 10));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
