package mete0r.android.content;

import android.content.ContentValues;
import android.database.Cursor;

public class ContentDirectoryWrapper implements ContentDirectory {

	ContentDirectory underlying;

	public ContentDirectoryWrapper(ContentDirectory underlying) {
		this.underlying = underlying;
	}

	public ContentDirectory underlying() {
		return underlying;
	}

	public void underlying(ContentDirectory underlying) {
		this.underlying = underlying;
	}

	@Override
	public long insert(ContentValues values) {
		return underlying.insert(values);
	}

	@Override
	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return underlying
				.query(projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public int update(ContentValues values, String selection,
			String[] selectionArgs) {
		return underlying.update(values, selection, selectionArgs);
	}

	@Override
	public int delete(String selection, String[] selectionArgs) {
		return underlying.delete(selection, selectionArgs);
	}
}
