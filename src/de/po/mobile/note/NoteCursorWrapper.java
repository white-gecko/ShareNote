package de.po.mobile.note;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/**
 * This wrapper adds an _id-column to the tabe and devides the content in title and content.
 * @author natanael
 *
 */
public class NoteCursorWrapper extends CursorWrapper implements Cursor {

	public NoteCursorWrapper(Cursor cursor) {
		super(cursor);
		// TODO Auto-generated constructor stub
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	public byte[] getBlob(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getColumnIndex(String columnName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getColumnIndexOrThrow(String columnName)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDouble(int columnIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Bundle getExtras() {
		// TODO Auto-generated method stub
		return null;
	}

	public float getFloat(int columnIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(int columnIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLong(int columnIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getShort(int columnIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getString(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getWantsAllOnMoveCalls() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAfterLast() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isBeforeFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLast() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNull(int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean move(int offset) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToLast() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToNext() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToPosition(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveToPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerContentObserver(ContentObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public boolean requery() {
		// TODO Auto-generated method stub
		return false;
	}

	public Bundle respond(Bundle extras) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNotificationUri(ContentResolver cr, Uri uri) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterContentObserver(ContentObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

}
