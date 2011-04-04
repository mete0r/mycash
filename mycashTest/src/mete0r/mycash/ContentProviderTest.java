package mete0r.mycash;

import mete0r.mycash.content.EventColumns;
import mete0r.mycash.content.EventContent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.test.InstrumentationTestCase;

public class ContentProviderTest extends InstrumentationTestCase {

	private static final String MIME_EVENT_DIR = "vnd.android.cursor.dir/"
			+ EventContent.CONTENT_SUBTYPE;
	private static final Uri CONTENT_URI = EventContent.CONTENT_URI;

	TestContext context;
	ContentProvider provider;

	public void setUp() {
		context = new TestContext(this);
		provider = new ContentProvider();
		provider.attachInfo(context, null);
	}

	public void testGetType() {
		assertEquals(MIME_EVENT_DIR, provider
				.getType(CONTENT_URI));
	}

	public void testInsert() {
		ContentValues values = new ContentValues();
		values.put(EventColumns.HOW_MUCH, 1000);
		values.put(EventColumns.WHEN, System.currentTimeMillis());
		values.put(EventColumns.METHOD, MethodValues.CASH.ordinal());
		Uri uri = provider.insert(CONTENT_URI, values);
		long id = ContentUris.parseId(uri);
		assertTrue(id > 0);
	}

	public void testResolution() {
		ContentResolver resolv = context.getContentResolver();
		assertEquals(MIME_EVENT_DIR, resolv
				.getType(CONTENT_URI));
	}
}
