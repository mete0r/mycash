package mete0r.mycash;

import mete0r.android.content.ContentDirectory;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ResolverContentDirectory implements ContentDirectory {

	final ContentResolver resolver;
	final Uri contentUri;

	public ResolverContentDirectory(ContentResolver resolver, Uri uri) {
		this.resolver = resolver;
		this.contentUri = uri;
	}

	@Override
	public long insert(ContentValues values) {
		return ContentUris.parseId(resolver.insert(contentUri, values));
	}

	@Override
	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return resolver.query(contentUri, projection, selection, selectionArgs,
				sortOrder);
	}

	@Override
	public int update(ContentValues values, String selection,
			String[] selectionArgs) {
		return resolver.update(contentUri, values, selection, selectionArgs);
	}

	@Override
	public int delete(String selection, String[] selectionArgs) {
		return resolver.delete(contentUri, selection, selectionArgs);
	}

}
