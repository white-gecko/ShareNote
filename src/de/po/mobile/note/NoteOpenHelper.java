package de.po.mobile.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteOpenHelper extends SQLiteOpenHelper {

	public static final String NOTES_TABLE = "notes";
	public static final String[] NOTES_TABLE_LAYOUT = {"_id", "uid", "title", "content", "creation", "modification"};
	
	private static final String DATABASE_NAME = "sharenote";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
        "create table "+ NOTES_TABLE + " (_id integer primary key, uid text, title text not null, "
        + "content text not null, creation integer not null, modification integer not null);";
	
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
	}

}
