package de.po.mobile.note;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NoteMapper {
	private static final String TAG = "NoteMapper";

	private HashMap<String, Note> notes;
	private NoteOpenHelper openHelper;
	private Cursor cursor = null;

	public NoteMapper(Context context) {
		openHelper = new NoteOpenHelper(context);
		notes = new HashMap<String, Note>();
	}

	public Cursor getCursor() {
		if (cursor == null) {
			SQLiteDatabase db = openHelper.getReadableDatabase();
			String table = NoteOpenHelper.NOTES_TABLE;
			String[] columns = NoteOpenHelper.NOTES_TABLE_LAYOUT;
			cursor = db.query(table, columns, null, null, null, null,
					"modification");
		}
		return cursor;
	}

	public Note getNote(long id) {
		String uid = _getUid(id);
		return getNote(uid);
	}

	public Note getNote(String id) {
		if (id == null) {
			return null;
		}

		if (notes.containsKey(id)) {
			Log.d(TAG, "Note with uid=" + id
					+ " already in hashmap returning it.");
			return notes.get(id);
		} else {
			Log.d(TAG, "Note with uid=" + id
					+ " not in hashmap getting it from db.");
			SQLiteDatabase db = openHelper.getReadableDatabase();
			// search for note with id in database
			String table = NoteOpenHelper.NOTES_TABLE;
			String[] columns = NoteOpenHelper.NOTES_TABLE_LAYOUT;
			String selection = "uid = ?";
			String[] selectionArgs = { id };
			Cursor result = db.query(table, columns, selection, selectionArgs,
					null, null, null);

			// add it to HashMap
			Note note = null;

			while (result.moveToNext()) {
				if (result.getString(1).equals(id)) {
					String content = result.getString(2)
							+ System.getProperty("line.separator");
					content += result.getString(3);
					Date creation = new Date(result.getLong(4));
					Date modification = new Date(result.getLong(5));
					note = new Note(id, content, creation, modification);
					Log.d(TAG, "Note with uid=" + id + " created.");
				} else {
					// log this because it shouldn't happen
					Log.e(TAG,
							"The uid=" + id + " I asked for is not equals uid="
									+ result.getString(1)
									+ ", which I got from DB.");
				}
			}

			result.close();
			db.close();
			notes.put(id, note);

			return note;
		}
	}

	public ArrayList<Note> searchNotes(String searchstring) {
		// search for id in database
		// return list with these notes
		return null;
	}

	public void saveNote(Note note) {
		notes.put(note.getId(), note);
		// write note to database
		String table = NoteOpenHelper.NOTES_TABLE;
		ContentValues values = new ContentValues();
		values.put("uid", note.getId());
		values.put("title", note.getTitle());
		values.put("content", note.getContent(false));
		values.put("creation", note.getCreation().getTime());
		values.put("modification", note.getModification().getTime());

		long dbId = _getDbId(note.getId());

		SQLiteDatabase db = openHelper.getWritableDatabase();
		if (dbId < 0) {
			Log.d(TAG, "The Note (" + note.getId()
					+ ") is not yet in DB, so we are creating a new entry.");
			db.insert(table, null, values);
		} else {
			Log.d(TAG, "The Note (" + note.getId() + ") has DbID=" + dbId
					+ ", so we will updated it.");
			String where = "_id = ?";
			String[] whereArgs = { Long.toString(dbId) };
			db.update(table, values, where, whereArgs);
		}
		db.close();

		/*
		 * if (cursor.requery()) { Log.v(TAG,
		 * "Successfully updated the cursor."); } else { Log.v(TAG,
		 * "Problem with updating the cursor."); }
		 */
	}

	/**
	 * @deprecated
	 * @param note
	 */
	public void deleteNote(Note note) {
		// in future we should just set a property in the note to deleted and
		// mark the note as deleted when it is saved
		notes.remove(note.getId());
		SQLiteDatabase db = openHelper.getWritableDatabase();
		String table = NoteOpenHelper.NOTES_TABLE;
		String where = "uid = ?";
		String[] whereArgs = { note.getId() };
		db.delete(table, where, whereArgs);
		db.close();
	}

	private String _getUid(long id) {
		// search for note with id in database
		String table = NoteOpenHelper.NOTES_TABLE;
		String[] columns = { "_id, uid" };
		String selection = "_id = ?";
		String[] selectionArgs = { Long.toString(id) };

		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor result = db.query(table, columns, selection, selectionArgs,
				null, null, null, "1");

		String uId = null;
		if (result.moveToFirst()) {
			Log.d(TAG, "There is at leased one entry with dbId=" + id
					+ " in the db.");
			uId = result.getString(1);
		}

		result.close();
		db.close();

		Log.d(TAG, "The Note with dbID=" + id + " has the uid=" + uId + ".");

		return uId;
	}

	/**
	 * If no Note with the given uid exists this function will return -1.
	 * 
	 * @param uid
	 * @return
	 */
	private long _getDbId(String uid) {
		// search for note with id in database
		String table = NoteOpenHelper.NOTES_TABLE;
		String[] columns = { "_id, uid" };
		String selection = "uid = ?";
		String[] selectionArgs = { uid };

		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor result = db.query(table, columns, selection, selectionArgs,
				null, null, null, "1");

		long dbId = -1;

		if (result.moveToFirst()) {
			dbId = result.getLong(0);
		}

		result.close();
		db.close();

		return dbId;
	}

}
