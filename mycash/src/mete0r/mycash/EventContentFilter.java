package mete0r.mycash;

import mete0r.android.content.ContentFilter;
import mete0r.mycash.content.EventColumns;
import android.content.ContentValues;

public class EventContentFilter implements ContentFilter, EventColumns {

	@Override
	public void onInsert(ContentValues values) {
		if (!values.containsKey(WHEN)) {
			values.put(WHEN, System.currentTimeMillis());
		}
		if (!values.containsKey(HOW_MUCH)) {
			values.put(HOW_MUCH, 0);
		}
		if (!values.containsKey(METHOD)) {
			values.put(METHOD, MethodValues.CASH.ordinal());
		}
		validate(values);
	}

	@Override
	public void onUpdate(ContentValues values) {
		validate(values);
	}

	public void validate(ContentValues values) {
		Integer method = values.getAsInteger(METHOD);
		if (method == null) {
			throw new IllegalArgumentException();
		}
		if (method < 0) {
			throw new IllegalArgumentException();
		}
		if (MethodValues.values().length < method) {
			throw new IllegalArgumentException();
		}
	}

}
