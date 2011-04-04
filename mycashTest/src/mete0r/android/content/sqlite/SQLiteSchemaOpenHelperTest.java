package mete0r.android.content.sqlite;

import mete0r.mycash.TestContext;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.InstrumentationTestCase;

public class SQLiteSchemaOpenHelperTest extends InstrumentationTestCase {

	private static final String SAMPLE_DB = "sample.db";

	TestContext context;

	public void setUp() throws Exception {
		super.setUp();
		context = new TestContext(this);
	}

	public void testDBOpenHelper() {
		SQLiteSchemaOpenHelper helper = new SQLiteSchemaOpenHelper(context,
				SAMPLE_DB, 1);
		assertEquals(1, helper.version);
	}

	public void testOnCreate() {
		SQLiteSchemaOpenHelper helper = new SQLiteSchemaOpenHelper(context,
				SAMPLE_DB, 1);
		helper.add(new SQLiteSchema() {

			@Override
			public void create(SQLiteDatabase db, int version) {
				db.execSQL("CREATE TABLE hello ( _id INTEGER PRIMARY KEY )");
			}

			@Override
			public void upgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
			}
		});
		SQLiteDatabase db = context.openOrCreateDatabase(SAMPLE_DB, 0, null);
		helper.onCreate(db);

		ContentValues values = new ContentValues();
		Cursor c = db.query("sqlite_master", null, "tbl_name=\"hello\"", null,
				null, null, null);
		try {
			if (c.moveToFirst()) {
				DatabaseUtils.cursorRowToContentValues(c, values);
			}
		} finally {
			c.close();
		}
	}

	public void testOnUpgrade() {
		SQLiteSchemaOpenHelper helper = new SQLiteSchemaOpenHelper(context,
				SAMPLE_DB, 1);
		SQLiteDatabase db = context.openOrCreateDatabase(SAMPLE_DB, 0, null);
		helper.onUpgrade(db, 0, 1);
	}

}
