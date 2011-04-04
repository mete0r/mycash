package mete0r.mycash;

import mete0r.android.content.FilteredContentProviderModule;
import mete0r.android.content.ModularContentProvider;
import mete0r.android.content.sqlite.SQLiteSchemaOpenHelper;
import mete0r.android.content.sqlite.SQLiteTableContentProviderModule;
import mete0r.mycash.content.Contents;
import mete0r.mycash.content.EventContent;
import mete0r.mycash.content.impl.EventTableSchema;
import android.content.ContentValues;
import android.net.Uri;


public class ContentProvider extends ModularContentProvider {

	static final EventTableSchema EVENT_TABLE_SCHEMA = new EventTableSchema();

	SQLiteSchemaOpenHelper helper;

	public ContentProvider() {
		super(Contents.AUTHORITY);
	}

	public boolean onCreate() {
		if (super.onCreate()) {
			helper = new SQLiteSchemaOpenHelper(getContext(), "mycash.db", 1);
			final SQLiteTableContentProviderModule eventModule =
				new SQLiteTableContentProviderModule(
					helper, EVENT_TABLE_SCHEMA,
					EventContent.CONTENT_SUBTYPE);
			addModule(EventContent.CONTENT_PATH,
					new FilteredContentProviderModule(eventModule,
							new EventContentFilter()));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri contentUri = super.insert(uri, values);
		getContext().getContentResolver().notifyChange(uri, null);
		getContext().getContentResolver().notifyChange(contentUri, null);
		return contentUri;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int n = super.update(uri, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return n;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int n = super.delete(uri, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return n;
	}
}
