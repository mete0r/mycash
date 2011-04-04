package mete0r.mycash;

import mete0r.MyCash.R;
import mete0r.mycash.content.EventContent;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class EventsActivity extends Activity {

	static final int DLG_METHOD = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView events = (ListView) findViewById(R.id.events);

		final ContentResolver resolv = getContentResolver();
		Cursor c = resolv.query(EventContent.CONTENT_URI, null, null, null,
				null);
		this.startManagingCursor(c);
		final EventAdapter eventAdapter = new EventAdapter(this, c);
		resolv.registerContentObserver(EventContent.CONTENT_URI, true,
				new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfchange) {
						Cursor c = resolv.query(EventContent.CONTENT_URI, null,
								null, null, null);
						eventAdapter.changeCursor(c);
					}
				});
		events.setAdapter(eventAdapter);

		events.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(EventsActivity.this,
						EventActivity.class);
				Cursor c = eventAdapter.getCursor();
				c.moveToPosition(position);
				ContentValues values = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(c, values);
				intent.putExtra("ContentValues", values);
				startActivity(intent);
			}
		});

		events.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				resolv.delete(ContentUris.withAppendedId(
						EventContent.CONTENT_URI, id), null, null);
				return true;
			}
		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(R.string.add);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(EventsActivity.this,
						EventActivity.class);
				startActivity(intent);
				return true;
			}
		});
		return true;
	}
}