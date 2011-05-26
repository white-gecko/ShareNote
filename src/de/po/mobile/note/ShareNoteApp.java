package de.po.mobile.note;

import android.app.Application;

public class ShareNoteApp extends Application {
	private NoteMapper nm = null;
	
	public NoteMapper getNoteMapper() {
		if (nm == null) {
			nm = new NoteMapper(getApplicationContext());
		}
		return nm;
	}
}
