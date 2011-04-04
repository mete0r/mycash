package mete0r.android.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class ContentCrudOnDirectory implements ContentCRUD {

	static final String SELECTION = BaseColumns._ID + "=?";

	static String[] getSelectionArgs(long id) {
		return new String[] { Long.toString(id) };
	}

	final ContentDirectory directory;

	public ContentCrudOnDirectory(ContentDirectory directory) {
		this.directory = directory;
	}

	@Override
	public void create(long id, ContentValues values) {
		values.put(BaseColumns._ID, id);
		if (id != directory.insert(values)) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Cursor retrieve(long id, String[] projection) {
		return directory.query(projection, SELECTION, getSelectionArgs(id),
				null);
	}

	@Override
	public int update(long id, ContentValues values) {
		return directory.update(values, SELECTION, getSelectionArgs(id));

	}

	@Override
	public int delete(long id) {
		return directory.delete(SELECTION, getSelectionArgs(id));
	}

}
