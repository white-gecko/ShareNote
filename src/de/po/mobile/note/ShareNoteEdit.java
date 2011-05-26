package de.po.mobile.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ShareNoteEdit extends Activity {
	private static final String TAG = "ShareNoteEdit";

	private Note note = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		_processIntent();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		_processIntent();
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		EditText text = (EditText) findViewById(R.id.noteText);

		boolean save = false;
		String textString = text.getText().toString();
		if (note == null && textString.length() > 0) {
			note = new Note("");
			note.setContent(textString);
			save = true;
		} else if (note != null && !textString.equals(note.getContent())) {
			note.setContent(textString);
			save = true;
		}

		if (note != null && save) {
			NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
			nm.saveNote(note);
			Toast toast = Toast.makeText(getApplicationContext(), "saved",
					Toast.LENGTH_SHORT);
			// toast.setText(R.string.noteSaved);
			toast.show();
			Log.v(TAG, "Note saved");
		} else {
			Log.v(TAG, "Don't need to save the note");
		}

		// toast "save note"
		super.onPause();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.note_context, menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.deleteNote:
			deleteNote(note.getId());
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void _processIntent() {
		EditText text = (EditText) findViewById(R.id.noteText);
		NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
		getIntent().hasExtra("uid");

		if (getIntent().hasExtra("dbid")) {
			long id = getIntent().getLongExtra("dbid", -1);
			if (id > 0) {
				note = nm.getNote(id);
			}
		} else if (getIntent().hasExtra("uid")) {
			String uId = getIntent().getStringExtra("uid");
			if (uId != null) {
				note = nm.getNote(uId);
			}
		}

		if (note != null) {
			text.setText(note.getContent());
		} else {
			text.setText("");
		}

	}
	
	private void deleteNote(String uid) {
		NoteMapper nm = ((ShareNoteApp)getApplication()).getNoteMapper();
		Note note = nm.getNote(uid);
		nm.deleteNote(note);
		note = null;
		finish();
	}
}
