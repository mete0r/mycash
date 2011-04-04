package mete0r.mycash.content.impl;

import mete0r.android.content.sqlite.SQLiteTableSchema;
import mete0r.mycash.content.EventColumns;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class EventTableSchema extends SQLiteTableSchema implements
		EventColumns {

	static final String NAME = "Event";
	static final String[] COLUMNS = {
			column(_ID, "INTEGER", "PRIMARY KEY"),
			column(WHEN, "INTEGER", Nullable.NOT_NULL),
			column(HOW_MUCH, "INTEGER", Nullable.NOT_NULL),
			column(METHOD, "INTEGER", Nullable.NOT_NULL),
			column(NOTE, "TEXT")
	};

	public EventTableSchema() {
		super(NAME);
	}


	@Override
	public void create(SQLiteDatabase db, int version) {
		String sql = "CREATE TABLE " + NAME + " ("
				+ TextUtils.join(",", COLUMNS) + ")";
		db.execSQL(sql);
	}

	@Override
	public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	static String column(String name, String type, String key) {
		return '"' + name + '"' + ' ' + type + ' ' + key;
	}

	static String column(String name, String type) {
		return '"' + name + '"' + ' ' + type;
	}

	enum Nullable {
		NULLABLE(""), NOT_NULL(" NOT NULL");

		String sql;
		Nullable(String sql) {
			this.sql = sql;
		}
	}
	static String column(String name, String type, Nullable nullable) {
		return column(name, type) + nullable.sql;
	}

}
