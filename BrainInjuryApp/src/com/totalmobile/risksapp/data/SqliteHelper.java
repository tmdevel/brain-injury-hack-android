package com.totalmobile.risksapp.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MindMateNI.db";
	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase dataBase;
	private final Context context;
	private final String dbPath;

	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		dbPath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		if (!dbExist) {
			super.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		InputStream input = context.getAssets().open(DATABASE_NAME);
		OutputStream output = new FileOutputStream(dbPath);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
		output.flush();
		output.close();
		input.close();
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		dataBase = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS
						| SQLiteDatabase.OPEN_READONLY);
		return super.getReadableDatabase();
	}

	@Override
	public synchronized void close() {
		if (dataBase != null)
			dataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
