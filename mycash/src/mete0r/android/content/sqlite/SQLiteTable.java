package mete0r.android.content.sqlite;

import mete0r.android.content.ContentDirectory;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteTable implements ContentDirectory {

	final SQLiteDatabase db;
	public final SQLiteTableSchema schema;

	public SQLiteTable(SQLiteDatabase db, SQLiteTableSchema schema) {
		this.db = db;
		this.schema = schema;
	}

	@Override
	public long insert(ContentValues values) {
		return db.insertOrThrow(schema.name, null, values);
	}

	@Override
	public Cursor query(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return db.query(schema.name, projection, selection, selectionArgs,
				null, null,
				sortOrder);
	}

	@Override
	public int update(ContentValues values, String selection,
			String[] selectionArgs) {
		return db.update(schema.name, values, selection, selectionArgs);
	}

	@Override
	public int delete(String selection, String[] selectionArgs) {
		return db.delete(schema.name, selection, selectionArgs);
	}

}
