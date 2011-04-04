package mete0r.android.content;

import android.content.ContentValues;
import android.database.Cursor;

public interface ContentCRUD {

	public void create(long id, ContentValues values);

	public Cursor retrieve(long id, String[] projection);

	public int update(long id, ContentValues values);

	public int delete(long id);
}
