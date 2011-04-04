package mete0r.android.content;

import android.content.ContentValues;
import android.database.Cursor;

public class ContentCRUDWrapper implements ContentCRUD {

	ContentCRUD underlying;

	public ContentCRUD underlying() {
		return underlying;
	}

	public void underlying(ContentCRUD underlying) {
		this.underlying = underlying;
	}

	@Override
	public void create(long id, ContentValues values) {
		underlying.create(id, values);
	}

	@Override
	public Cursor retrieve(long id, String[] projection) {
		return underlying.retrieve(id, projection);
	}

	@Override
	public int update(long id, ContentValues values) {
		return underlying.update(id, values);
	}

	@Override
	public int delete(long id) {
		return underlying.delete(id);
	}

}
