package mete0r.android.content.sqlite;

import mete0r.android.content.ContentDirectory;
import mete0r.android.content.ContentProviderModule;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteTableContentProviderModule extends ContentProviderModule {

	final SQLiteSchemaOpenHelper dbHelper;
	final SQLiteTableSchema schema;

	public SQLiteTableContentProviderModule(SQLiteSchemaOpenHelper dbHelper,
			SQLiteTableSchema schema, String subtype) {
		super(subtype);

		dbHelper.add(schema);
		this.dbHelper = dbHelper;
		this.schema = schema;

	}

	@Override
	public ContentDirectory directory() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return schema.instance(db);
	}

}
