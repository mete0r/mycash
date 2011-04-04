package mete0r.android.content.sqlite;

import java.util.ArrayList;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteSchemaOpenHelper extends SQLiteOpenHelper {
	
	final String name;
	final int version;
	final ArrayList<SQLiteSchema> schemas = new ArrayList<SQLiteSchema>();

	public SQLiteSchemaOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
		this.name = name;
		this.version = version;
	}

	public void add(SQLiteSchema schema) {
		schemas.add(schema);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (SQLiteSchema schema : schemas) {
			schema.create(db, version);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (SQLiteSchema schema : schemas) {
			schema.upgrade(db, oldVersion, newVersion);
		}
	}

}
