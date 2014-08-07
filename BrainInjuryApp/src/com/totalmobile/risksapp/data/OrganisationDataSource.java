package com.totalmobile.risksapp.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.totalmobile.risksapp.entities.Constants;
import com.totalmobile.risksapp.entities.DbEntity;
import com.totalmobile.risksapp.entities.Organisation;

public class OrganisationDataSource extends DataSource {

	public static final String COLUMN_HASH = "hash";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CONTACT = "contact";
	public static final String COLUMN_ADDRESS1 = "address1";
	public static final String COLUMN_ADDRESS2 = "address2";
	public static final String COLUMN_TOWN = "town";
	public static final String COLUMN_COUNTY = "county";
	public static final String COLUMN_POSTCODE = "postcode";
	public static final String COLUMN_TELEPHONE = "telephone";
	public static final String COLUMN_FAX = "fax";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_WEBSITE = "website";
	public static final String COLUMN_AGE_GROUPS = "ageGroups";
	public static final String COLUMN_OVERVIEW_OF_SERVICES = "overviewOfServices";
	public static final String COLUMN_LONGITUDE = "long";
	public static final String COLUMN_LATITUDE = "lat";

	public OrganisationDataSource(Context context) {
		super(context);
	}

	private static final String getOrganisationsFilteredByCategoryBase = "SELECT * FROM Organisation "
			+ "INNER JOIN CategoryOrganisation ON Organisation._id = CategoryOrganisation.organisationId ";

	private static final String whereOrganisationName = " Organisation.name LIKE ?";
	private static final String whereOrganisationLocation = " Organisation.long BETWEEN ? AND ? AND Organisation.lat BETWEEN ? AND ?";
	private static final String whereCategoryId = " CategoryOrganisation.categoryId = ?";
	private static final String getAllOrganisations = "SELECT * FROM Organisation ";

	@Override
	protected String setTableName() {
		return "Organisation";
	}

	public List<Organisation> getOrganisations(int categoryId,
			double currentLong, double currentLat, double radius,
			String organisationName) {
		List<Organisation> organisations = new ArrayList<Organisation>();
		Cursor cursor;

		if (!(organisationName == null || organisationName.length() == 0)) {
			String[] where = {
					String.valueOf(categoryId),
					String.valueOf(currentLong - radius
							* Constants.LONG_DEGREE_PER_UNIT),
					String.valueOf(currentLong + radius
							* Constants.LONG_DEGREE_PER_UNIT),
					String.valueOf(currentLat - radius
							* Constants.LAT_DEGREE_PER_UNIT),
					String.valueOf(currentLat + radius
							* Constants.LAT_DEGREE_PER_UNIT) };

			String sql = getOrganisationsFilteredByCategoryBase + "WHERE"
					+ whereCategoryId + " AND" + whereOrganisationLocation;
			cursor = database.rawQuery(sql, where);
		}

		else {
			if (radius <= 0) {
				String[] where = { "%" + organisationName + "%" };
				String sql = getAllOrganisations + "WHERE"
						+ whereOrganisationName;
				cursor = database.rawQuery(sql, where);
			}

			else {
				String[] where = {
						String.valueOf(categoryId),
						"%" + organisationName + "%",
						String.valueOf(currentLong - radius
								* Constants.LONG_DEGREE_PER_UNIT),
						String.valueOf(currentLong + radius
								* Constants.LONG_DEGREE_PER_UNIT),
						String.valueOf(currentLat - radius
								* Constants.LAT_DEGREE_PER_UNIT),
						String.valueOf(currentLat + radius
								* Constants.LAT_DEGREE_PER_UNIT) };

				String sql = getOrganisationsFilteredByCategoryBase + "WHERE"
						+ whereCategoryId + " AND" + whereOrganisationName
						+ " AND" + whereOrganisationLocation;
				cursor = database.rawQuery(sql, where);
			}
		}

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Organisation organisation = cursorToEntity(cursor, currentLong,
					currentLat);
			organisations.add(organisation);
			cursor.moveToNext();
		}

		cursor.close();
		sortOrganisationsByDistance(organisations, radius <= 0 ? null : radius);
		return organisations;
	}

	public Organisation getOrganisationById(int organisationId,
			double currentLong, double currentLat) {
		Organisation organisation = null;
		Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_ID
				+ " = ?", new String[] { String.valueOf(organisationId) },
				null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			organisation = cursorToEntity(cursor, currentLong, currentLat);
		}
		return organisation;
	}

	@Override
	protected ContentValues populateContentValues(DbEntity entity) {
		if (!(entity instanceof Organisation))
			return null;
		Organisation organisation = (Organisation) entity;
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, organisation.getId());
		values.put(COLUMN_HASH, organisation.getHash());
		values.put(COLUMN_NAME, organisation.getName());
		values.put(COLUMN_CONTACT, organisation.getContact());
		values.put(COLUMN_ADDRESS1, organisation.getAddress1());
		values.put(COLUMN_ADDRESS2, organisation.getAddress2());
		values.put(COLUMN_TOWN, organisation.getTown());
		values.put(COLUMN_COUNTY, organisation.getCounty());
		values.put(COLUMN_POSTCODE, organisation.getPostcode());
		values.put(COLUMN_TELEPHONE, organisation.getTelephone());
		values.put(COLUMN_FAX, organisation.getFax());
		values.put(COLUMN_EMAIL, organisation.getEmail());
		values.put(COLUMN_WEBSITE, organisation.getWebsite());
		values.put(COLUMN_AGE_GROUPS, organisation.getAgeGroups());
		values.put(COLUMN_OVERVIEW_OF_SERVICES,
				organisation.getOverviewOfServices());
		values.put(COLUMN_LONGITUDE, organisation.getLongitude());
		values.put(COLUMN_LATITUDE, organisation.getLatitude());
		return values;
	}

	@Override
	protected String[] populateAllColumns() {
		String[] allColumns = { COLUMN_ID, COLUMN_HASH, COLUMN_NAME,
				COLUMN_CONTACT, COLUMN_ADDRESS1, COLUMN_ADDRESS2, COLUMN_TOWN,
				COLUMN_COUNTY, COLUMN_POSTCODE, COLUMN_TELEPHONE, COLUMN_FAX,
				COLUMN_EMAIL, COLUMN_WEBSITE, COLUMN_AGE_GROUPS,
				COLUMN_OVERVIEW_OF_SERVICES, COLUMN_LONGITUDE, COLUMN_LATITUDE };
		return allColumns;
	}

	protected Organisation cursorToEntity(Cursor cursor, double currentLong,
			double currentLat) {
		if (columnNameIndexDictionary == null)
			populateColumnNameIndexDictionary(cursor);
		Organisation organisation = new Organisation();
		organisation.setId(cursor.getInt(columnNameIndexDictionary
				.get(COLUMN_ID)));
		organisation.setName(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_NAME)));
		organisation.setContact(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_CONTACT)));
		organisation.setAddress1(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_ADDRESS1)));
		organisation.setAddress2(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_ADDRESS2)));
		organisation.setTown(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_TOWN)));
		organisation.setCounty(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_COUNTY)));
		organisation.setPostcode(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_POSTCODE)));
		organisation.setTelephone(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_TELEPHONE)));
		organisation.setFax(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_FAX)));
		organisation.setEmail(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_EMAIL)));
		organisation.setWebsite(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_WEBSITE)));
		organisation.setAgeGroups(cursor.getString(columnNameIndexDictionary
				.get(COLUMN_AGE_GROUPS)));
		organisation.setOverviewOfServices(cursor
				.getString(columnNameIndexDictionary
						.get(COLUMN_OVERVIEW_OF_SERVICES)));

		double longitudeInDegrees = cursor.getDouble(columnNameIndexDictionary
				.get(COLUMN_LONGITUDE));
		double latitudeInDegrees = cursor.getDouble(columnNameIndexDictionary
				.get(COLUMN_LATITUDE));

		organisation.setLongitude((int) (longitudeInDegrees * 1000000));
		organisation.setLatitude((int) (latitudeInDegrees * 1000000));
		double longDistance = Math.abs(currentLong - longitudeInDegrees)
				/ Constants.LONG_DEGREE_PER_UNIT;
		double latDistance = Math.abs(currentLat - latitudeInDegrees)
				/ Constants.LAT_DEGREE_PER_UNIT;
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		double distance = Double.valueOf(twoDForm.format(Math.sqrt(longDistance
				* longDistance + latDistance * latDistance)));
		organisation.setDistance(distance);

		return organisation;
	}

	@Override
	protected void populateColumnNameIndexDictionary(Cursor cursor) {
		columnNameIndexDictionary = new Hashtable<String, Integer>();
		columnNameIndexDictionary.put(COLUMN_ID, 0);
		columnNameIndexDictionary.put(COLUMN_HASH,
				cursor.getColumnIndex(COLUMN_HASH));
		columnNameIndexDictionary.put(COLUMN_NAME,
				cursor.getColumnIndex(COLUMN_NAME));
		columnNameIndexDictionary.put(COLUMN_CONTACT,
				cursor.getColumnIndex(COLUMN_CONTACT));
		columnNameIndexDictionary.put(COLUMN_ADDRESS1,
				cursor.getColumnIndex(COLUMN_ADDRESS1));
		columnNameIndexDictionary.put(COLUMN_ADDRESS2,
				cursor.getColumnIndex(COLUMN_ADDRESS2));
		columnNameIndexDictionary.put(COLUMN_TOWN,
				cursor.getColumnIndex(COLUMN_TOWN));
		columnNameIndexDictionary.put(COLUMN_COUNTY,
				cursor.getColumnIndex(COLUMN_COUNTY));
		columnNameIndexDictionary.put(COLUMN_POSTCODE,
				cursor.getColumnIndex(COLUMN_POSTCODE));
		columnNameIndexDictionary.put(COLUMN_TELEPHONE,
				cursor.getColumnIndex(COLUMN_TELEPHONE));
		columnNameIndexDictionary.put(COLUMN_FAX,
				cursor.getColumnIndex(COLUMN_FAX));
		columnNameIndexDictionary.put(COLUMN_EMAIL,
				cursor.getColumnIndex(COLUMN_EMAIL));
		columnNameIndexDictionary.put(COLUMN_WEBSITE,
				cursor.getColumnIndex(COLUMN_WEBSITE));
		columnNameIndexDictionary.put(COLUMN_AGE_GROUPS,
				cursor.getColumnIndex(COLUMN_AGE_GROUPS));
		columnNameIndexDictionary.put(COLUMN_OVERVIEW_OF_SERVICES,
				cursor.getColumnIndex(COLUMN_OVERVIEW_OF_SERVICES));
		columnNameIndexDictionary.put(COLUMN_LONGITUDE,
				cursor.getColumnIndex(COLUMN_LONGITUDE));
		columnNameIndexDictionary.put(COLUMN_LATITUDE,
				cursor.getColumnIndex(COLUMN_LATITUDE));
	}

	private void sortOrganisationsByDistance(List<Organisation> organisations,
			Double radius) {
		if (organisations == null || organisations.size() < 2)
			return;

		for (int i = 0; i < organisations.size() - 1; ++i) {
			for (int j = i + 1; j < organisations.size(); ++j) {
				if (organisations.get(j).getDistance() < organisations.get(i)
						.getDistance()) {
					Organisation orgI = organisations.get(i);
					Organisation orgJ = organisations.get(j);

					organisations.remove(i);
					organisations.add(i, orgJ);
					organisations.remove(j);
					organisations.add(j, orgI);
				}
			}
		}

		if (radius == null)
			return;

		while (organisations.get(organisations.size() - 1).getDistance() > radius) {
			organisations.remove(organisations.size() - 1);
		}
	}

}
