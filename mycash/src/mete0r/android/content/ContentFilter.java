package mete0r.android.content;

import android.content.ContentValues;

public interface ContentFilter {

	public void onInsert(ContentValues values);

	public void onUpdate(ContentValues values);

}
