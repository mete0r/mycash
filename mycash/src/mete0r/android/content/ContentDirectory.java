package mete0r.android.content;

import android.content.ContentValues;
import android.database.Cursor;

public interface ContentDirectory {

	public long insert(ContentValues values);

	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder);

	public int update(ContentValues values, String selection,
			String[] selectionArgs);

	public int delete(String selection, String[] selectionArgs);
}
