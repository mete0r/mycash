package mete0r.mycash;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mete0r.MyCash.R;
import mete0r.android.content.ContentCRUD;
import mete0r.android.content.ContentCrudOnDirectory;
import mete0r.mycash.content.EventColumns;
import mete0r.mycash.content.EventContent;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EventActivity extends Activity implements EventColumns {

	EditText viewWhen;
	EditText viewHowMuch;
	RadioGroup viewMethod;
	EditText viewNote;

	final SimpleDateFormat fmtWhen = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	final NumberFormat fmtHowMuch = NumberFormat.getIntegerInstance();

	boolean edit;
	ContentValues values;

	ContentResolver resolver;
	ResolverContentDirectory directory;
	ContentCRUD crud;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);

		viewWhen = (EditText) findViewById(R.id.when);
		viewHowMuch = (EditText) findViewById(R.id.how_much);
		viewMethod = (RadioGroup) findViewById(R.id.method);
		viewNote = (EditText) findViewById(R.id.note);

		resolver = getContentResolver();
		directory = new ResolverContentDirectory(resolver,
				EventContent.CONTENT_URI);
		crud = new ContentCrudOnDirectory(directory);

		Intent intent = getIntent();
		values = intent.getParcelableExtra("ContentValues");
		if (values == null) {
			edit = false;
			values = new ContentValues();
		} else {
			edit = true;
		}

		load();

		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				save();

			}
		});
	}

	void load() {
		Long when = values.getAsLong(WHEN);
		if (when != null) {
			viewWhen.setText(fmtWhen.format(new Date(when)));
		}

		Integer howMuch = values.getAsInteger(HOW_MUCH);
		if (howMuch != null) {
			viewHowMuch.setText(fmtHowMuch.format(howMuch));
		}

		Integer methodOrd = values.getAsInteger(METHOD);
		if (methodOrd != null) {
			MethodValues method = MethodValues.values()[methodOrd];
			switch (method) {
			case CASH:
				viewMethod.check(R.id.radio0);
				break;
			case CREDIT_CARD:
				viewMethod.check(R.id.radio1);
				break;
			}
		}

		String note = values.getAsString(NOTE);
		if (note != null) {
			viewNote.setText(note);
		}
	}

	void save() {
		try {
			final String strWhen = viewWhen.getText().toString().trim();
			Date when;
			if (strWhen.length() > 0) {
				when = fmtWhen.parse(strWhen);
			} else {
				when = new Date(System.currentTimeMillis());
			}
			values.put(WHEN, when.getTime());
		} catch (ParseException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			viewWhen.requestFocus();
			return;
		}

		try {
			Number howMuch = fmtHowMuch.parse(viewHowMuch.getText().toString());
			values.put(HOW_MUCH, howMuch.intValue());
		} catch (ParseException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			viewHowMuch.requestFocus();
			return;
		}

		MethodValues method = MethodValues.CASH;
		switch (viewMethod.getCheckedRadioButtonId()) {
		case R.id.radio0:
			method = MethodValues.CASH;
			break;
		case R.id.radio1:
			method = MethodValues.CREDIT_CARD;
			break;
		case -1:
			Toast.makeText(this, "Choose a method", Toast.LENGTH_LONG).show();
			return;
		}
		values.put(METHOD, method.ordinal());

		values.put(NOTE, viewNote.getText().toString());

		if (values.containsKey(_ID)) {
			long id = values.getAsLong(_ID);
			crud.update(id, values);
			Toast.makeText(EventActivity.this, "The item has been saved.",
					Toast.LENGTH_LONG).show();

		} else {
			directory.insert(values);
			Toast.makeText(EventActivity.this, "New item has been added.",
					Toast.LENGTH_LONG).show();

		}

		finish();

	}
}
