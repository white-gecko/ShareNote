package de.po.mobile.note;

import java.text.DateFormat;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class ShareNoteList extends ListActivity {
	private static final String TAG = "ShareNoteListActivity";

	private Cursor result;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		registerForContextMenu(getListView());

		Button newNote = (Button) findViewById(R.id.newNote);
		newNote.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ShareNoteEdit.class);
				intent.putExtra("dbid", (long) -1);
				startActivity(intent);
			}
		});

		String[] from = { "title", "modification", "creation" };
		int[] to = { R.id.firstLine, R.id.secondLine1, R.id.secondLine2 };

		// NoteMapper nm = ((ShareNoteApp)getApplication()).getNoteMapper();

		NoteOpenHelper openHelper = new NoteOpenHelper(getApplicationContext());
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String table = NoteOpenHelper.NOTES_TABLE;
		String[] columns = NoteOpenHelper.NOTES_TABLE_LAYOUT;
		result = db.query(table, columns, null, null, null, null,
				"modification DESC");

		// Cursor result = nm.getCursor();
		startManagingCursor(result);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getApplicationContext(), R.layout.list_note_item, result, from,
				to);

		adapter.setViewBinder(new ViewBinder() {

			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				if (columnIndex == 4 || columnIndex == 5) {
					long date = cursor.getLong(columnIndex);
					String formatedDate = DateFormat.getInstance().format(date);
					// String formatedDate =
					// DateFormat.getDateInstance().format(date);
					// formatedDate +=
					// DateFormat.getTimeInstance().format(date);
					((TextView) view).setText(formatedDate);
					return true;
				}
				return false;
			}
		});

		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.v(TAG, "pos: " + position + " id: " + id);
		showNote(id);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.showNote:
			showNote(info.id);
			break;
		case R.id.deleteNote:
			deleteNote(info.id);
			break;
		case R.id.shareNote:
			shareNote(info.id);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.note_list_context, menu);
		inflater.inflate(R.menu.note_context, menu);
	}

	private void showNote(long id) {
		Intent intent = new Intent(getApplicationContext(), ShareNoteEdit.class);
		intent.putExtra("dbid", id);
		startActivity(intent);
	}

	private void deleteNote(long id) {
		NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
		Note note = nm.getNote(id);
		nm.deleteNote(note);
		result.requery();
	}

	private void shareNote(long id) {
		NoteMapper nm = ((ShareNoteApp) getApplication()).getNoteMapper();
		Note note = nm.getNote(id);

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
}