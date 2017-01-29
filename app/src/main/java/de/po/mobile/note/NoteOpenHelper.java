package de.po.mobile.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "NoteOpenHelper";

	public static final String NOTES_TABLE = "notes";
	public static final String[] NOTES_TABLE_LAYOUT = { "_id", "uid", "title",
			"content", "creation", "modification", "deleted" };

	private static final String DATABASE_NAME = "sharenote";
	private static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE = "create table "
			+ NOTES_TABLE
			+ " (_id integer primary key, uid text, title text not null, "
			+ "content text not null, creation integer not null, modification integer not null, deleted integer not null);";

	public NoteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// migrating data if there was an old version
		// version 1: add new integer column deleted and set it to false (0) as
		// default
		try {
			if (oldVersion <= 1) {
				Log.v(TAG, "Upgrading table '" + NOTES_TABLE + "' from "
						+ oldVersion + " to " + newVersion);
				db.execSQL("alter table " + NOTES_TABLE
						+ " add column deleted integer default 0;");
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "Problem upgrading Database", e);
			// should create sql dump to sdcard
			db.execSQL("alter table '" + NOTES_TABLE + "' rename to '" + NOTES_TABLE + "_" + oldVersion + "';");
			onCreate(db);
			Log.e(TAG, "Problem upgrading Database", e);
		}
	}

}
