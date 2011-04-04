package mete0r.android.content;

import android.content.ContentValues;

public class FilteredContentDirectory extends ContentDirectoryWrapper {

	ContentFilter filter;

	public FilteredContentDirectory(ContentDirectory underlying) {
		super(underlying);
	}

	public ContentFilter filter() {
		return filter;
	}

	public void filter(ContentFilter filter) {
		this.filter = filter;
	}

	@Override
	public final long insert(ContentValues values) {
		if (filter != null) {
			filter.onInsert(values);
		}
		return super.insert(values);
	}

	@Override
	public final int update(ContentValues values, String selection,
			String[] selectionArgs) {
		if (filter != null) {
			filter.onUpdate(values);
		}
		return super.update(values, selection, selectionArgs);
	}

}
