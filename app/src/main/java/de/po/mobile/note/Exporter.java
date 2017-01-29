package de.po.mobile.note;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by natanael on 29.01.17.
 */

public class Exporter {
    private static final String TAG = "Exporter";

    private NoteMapper noteMapper;
    private Context context;

    public Exporter(Context context, NoteMapper noteMapper) {
        this.context = context;
        this.noteMapper = noteMapper;
    }

    public void export() {
        File base = Environment.getExternalStorageDirectory();
        Log.v(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
        File exportfile = new File(base, "export-sharenote.xml");
        try {
            FileWriter writer = new FileWriter(exportfile);
            Cursor cursor = noteMapper.getCursor();
            String[] from = { "uid", "title", "content", "modification", "creation", "deleted" };
            while (cursor.moveToNext()) {
                writer.write(cursor.getColumnIndex("uid"));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("uid")));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("title")));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("content")));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("modification")));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("creation")));
                Log.v(TAG, cursor.getString(cursor.getColumnIndex("deleted")));
            }
            cursor.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
