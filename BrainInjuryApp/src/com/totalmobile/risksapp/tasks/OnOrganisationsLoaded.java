package com.totalmobile.risksapp.tasks;

import java.util.List;

import com.totalmobile.risksapp.entities.Organisation;

public interface OnOrganisationsLoaded {
	
	void perform(List<Organisation> organisations);

}
