package de.po.mobile.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ShareNoteEdit extends Activity {
	private static final String TAG = "ShareNoteEdit";

	private Note note = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.edit);
		note = (Note) getLastNonConfigurationInstance();
		_processIntent();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// return super.onRetainNonConfigurationInstance();
		return note;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent");
		setIntent(intent);
		_processIntent();
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		saveNote();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.note_context, menu);
		MenuCompat.setShowAsAction(menu.findItem(R.id.deleteNote), 1);
		MenuCompat.setShowAsAction(menu.findItem(R.id.shareNote), 1);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.deleteNote:
			deleteNote();
			break;
		case R.id.shareNote:
			shareNote();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void _processIntent() {
		EditText textView = (EditText) findViewById(R.id.noteText);
		NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
		// getIntent().hasExtra("uid");

		Intent intent = getIntent();
		String action = intent.getAction();
		String content = "";

		if (action != null && action.equals(android.content.Intent.ACTION_SEND)) {
			String title = intent
					.getStringExtra(android.content.Intent.EXTRA_TITLE);
			String text = intent
					.getStringExtra(android.content.Intent.EXTRA_TEXT);
			if (title != null) {
				content += title;
			}
			if (textView != null) {
				content += text;
			}
		} else {
			// action should be de.po.mobile.note.openNote
			if (intent.hasExtra("dbid")) {
				long id = intent.getLongExtra("dbid", -1);
				if (id > 0) {
					note = nm.getNote(id);
				}
			} else if (intent.hasExtra("uid")) {
				String uId = intent.getStringExtra("uid");
				if (uId != null) {
					note = nm.getNote(uId);
				}
			}
		}

		if (note != null) {
			textView.setText(note.getContent());
		} else {
			textView.setText(content);
		}

	}

	private void deleteNote() {
		saveNote();
		// NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
		// Note note = nm.getNote(uid);
		if (note != null) {
			note.delete();
			NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
			nm.saveNote(note);

			// toast "save note"
			Toast toast = Toast.makeText(getApplicationContext(), "Deleted",
					Toast.LENGTH_SHORT);
			// toast.setText(R.string.noteSaved);
			toast.show();
			Log.v(TAG, "Note deleted");
		}
		// nm.deleteNote(note);
		// note = null;
		finish();
	}

	private void shareNote() {
		saveNote();

		if (note != null) {
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					note.getTitle());
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					note.getContent());

			startActivity(Intent.createChooser(shareIntent,
					getString(R.string.shareNote)));
		} else {
			// toast "save note"
			Toast toast = Toast.makeText(getApplicationContext(),
					"Nothing to share", Toast.LENGTH_SHORT);
			// toast.setText(R.string.noteSaved);
			toast.show();
		}
	}

	public void saveNote() {

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

			// toast "save note"
			Toast toast = Toast.makeText(getApplicationContext(), "Saved",
					Toast.LENGTH_SHORT);
			// toast.setText(R.string.noteSaved);
			toast.show();
			Log.v(TAG, "Note saved");
		} else {
			Log.v(TAG, "Don't need to save the note");
		}
	}
}
