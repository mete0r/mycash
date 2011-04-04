package mete0r.android.content;

import java.util.ArrayList;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ModularContentProvider extends android.content.ContentProvider {

	public static final String MIME_CURSOR_ITEM = "vnd.android.cursor.item";
	public static final String MIME_CURSOR_DIR = "vnd.android.cursor.dir";

	final String authority;
	final ArrayList<ContentProviderModule> modules = new ArrayList<ContentProviderModule>();
	final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	public ModularContentProvider(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	public Uri getContentUri(String contentPath) {
		return new Uri.Builder().scheme("content").authority(authority).path(
				contentPath).build();
	}

	enum Method {
		DIR(""), ITEM("/#");
		public final String pathSuffix;
		Method(String pathSuffix) {
			this.pathSuffix = pathSuffix;
		}
	}

	public synchronized int addModule(String contentPath, ContentProviderModule module) {

		final int which = modules.size();
		modules.add(module);
		for (Method method : Method.values()) {
			final int code = which << 8 | method.ordinal();
			matcher.addURI(authority, contentPath + method.pathSuffix, code);
		}
		return which;

	}

	@Override
	public synchronized String getType(Uri uri) {

		final int code = matcher.match(uri);
		if (code == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException();
		}
		final int which = code >> 8;
		final int method = code & 0xff;
		final ContentProviderModule module = modules.get(which);
		switch (Method.values()[method]) {
		case DIR:
			return MIME_CURSOR_DIR + '/' + module.subtype;
		case ITEM:
			return MIME_CURSOR_ITEM + '/' + module.subtype;
		default:
			return null;
		}

	}

	@Override
	public synchronized Uri insert(Uri uri, ContentValues values) {
		final int code = matcher.match(uri);
		if (code == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException();
		}
		final int which = code >> 8;
		final int method = code & 0xff;
		final ContentProviderModule module = modules.get(which);
		switch (Method.values()[method]) {
		case DIR:
			return ContentUris.withAppendedId(uri, module.directory().insert(
					values));
		case ITEM:
			long id = ContentUris.parseId(uri);
			module.crud().create(id, values);
			return uri;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection,
			String selection,
			String[] selectionArgs, String sortOrder) {

		final int code = matcher.match(uri);
		if (code == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException();
		}
		final int which = code >> 8;
		final int method = code & 0xff;
		final ContentProviderModule ctp = modules.get(which);
		switch (Method.values()[method]) {
		case DIR:
			return ctp.directory().query(projection, selection, selectionArgs,
					sortOrder);
		case ITEM:
			long id = ContentUris.parseId(uri);
			return ctp.crud().retrieve(id, projection);
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public synchronized int update(Uri uri, ContentValues values,
			String selection,
			String[] selectionArgs) {
		final int code = matcher.match(uri);
		if (code == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException();
		}
		final int which = code >> 8;
		final int method = code & 0xff;
		final ContentProviderModule module = modules.get(which);
		switch (Method.values()[method]) {
		case DIR:
			return module.directory().update(values, selection, selectionArgs);
		case ITEM:
			long id = ContentUris.parseId(uri);
			return module.crud().update(id, values);
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final int code = matcher.match(uri);
		if (code == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException();
		}
		final int which = code >> 8;
		final int method = code & 0xff;
		final ContentProviderModule module = modules.get(which);
		switch (Method.values()[method]) {
		case DIR:
			return module.directory().delete(selection, selectionArgs);
		case ITEM:
			long id = ContentUris.parseId(uri);
			return module.crud().delete(id);
		default:
			throw new IllegalArgumentException();
		}
	}

}
