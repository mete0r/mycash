package mete0r.android.content.sqlite;

import android.database.sqlite.SQLiteDatabase;

public abstract class SQLiteTableSchema implements SQLiteSchema {

	public final String name;

	public SQLiteTableSchema(String name) {
		this.name = name;
	}

	public SQLiteTable instance(SQLiteDatabase db) {
		return new SQLiteTable(db, this);
	}

}
