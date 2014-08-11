package com.totalmobile.risksapp.data;

import java.io.IOException;
import java.util.Dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.totalmobile.risksapp.entities.DbEntity;
import com.totalmobile.risksapp.entities.Organisation;

public abstract class DataSource {
	
	public static final String COLUMN_ID = "_id";
	protected static String TABLE_NAME;
	
	protected SQLiteDatabase database;
	private SqliteHelper dbHelper;
	protected String[] allColumns;
	
	protected Dictionary<String, Integer> columnNameIndexDictionary;
	
	public DataSource(Context context) {
		dbHelper = new SqliteHelper(context);
		try {
			dbHelper.createDataBase();
		} 
		catch (IOException e) {

		}
		TABLE_NAME = setTableName();
		allColumns = populateAllColumns();
	}
	
	public void open() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
	    dbHelper.close();
	}

	public void createInstance(DbEntity entity) {
		ContentValues values = populateContentValues(entity);	    
		database.insert(TABLE_NAME, null, values);
	}


	public void deleteOrganisation(Organisation organisation) {
		database.delete(TABLE_NAME, COLUMN_ID + " = " + organisation.getId(), null);
	}
	
	public void updateEntity(DbEntity entity) {

	}
	
	protected abstract String setTableName();
	protected abstract ContentValues populateContentValues(DbEntity entity);
	protected abstract String[] populateAllColumns();
	protected abstract void populateColumnNameIndexDictionary(Cursor cursor);
}
