package com.totalmobile.risksapp.tasks;

import android.os.AsyncTask;

public class WaitForLocation extends AsyncTask<Long, Void, Void> {
	
	private OnWaitForLocationFinished onWaitForLocationFinished;
	
	public WaitForLocation(OnWaitForLocationFinished onWaitForLocationFinished) {
		this.onWaitForLocationFinished = onWaitForLocationFinished;
	}

	@Override
	protected Void doInBackground(Long... params) {
		try {
			Thread.sleep(params[0]);
		} catch (InterruptedException e) {}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		onWaitForLocationFinished.perform();
	}
}
