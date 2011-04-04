package mete0r.android.content.sqlite;

import android.database.sqlite.SQLiteDatabase;

public interface SQLiteSchema {

	public void create(SQLiteDatabase db, int version);

	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion);

}
