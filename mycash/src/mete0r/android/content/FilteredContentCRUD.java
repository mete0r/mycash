package mete0r.android.content;

import android.content.ContentValues;

public class FilteredContentCRUD extends ContentCRUDWrapper {

	ContentFilter filter;

	public ContentFilter filter() {
		return filter;
	}

	public void filter(ContentFilter filter) {
		this.filter = filter;
	}

	public void create(long id, ContentValues values) {
		if (filter != null) {
			filter.onInsert(values);
		}
		super.create(id, values);
	}

	public int update(long id, ContentValues values) {
		if (filter != null) {
			filter.onUpdate(values);
		}
		return super.update(id, values);
	}
}
