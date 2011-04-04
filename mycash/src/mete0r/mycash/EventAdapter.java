package mete0r.mycash;

import java.text.ChoiceFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mete0r.MyCash.R;
import mete0r.mycash.content.EventColumns;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EventAdapter extends ResourceCursorAdapter implements EventColumns {

	final int idxWhen;
	final int idxHowMuch;
	final int idxMethod;
	final int idxNote;

	final SimpleDateFormat fmtWhen = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	final NumberFormat fmtHowMuch = NumberFormat.getCurrencyInstance();
	final ChoiceFormat fmtMethod = new ChoiceFormat(new double[] { 0, 1 },
			new String[] { "Cash", "Credit card" });

	public EventAdapter(Context context, Cursor c) {
		super(context, R.layout.event_item, c);

		idxWhen = c.getColumnIndexOrThrow(WHEN);
		idxHowMuch = c.getColumnIndexOrThrow(HOW_MUCH);
		idxMethod = c.getColumnIndexOrThrow(METHOD);
		idxNote = c.getColumnIndexOrThrow(NOTE);

	}

	@Override
	public void bindView(View view, Context context, Cursor c) {

		TextView viewWhen = (TextView) view.findViewById(R.id.when);
		TextView viewHowMuch = (TextView) view.findViewById(R.id.how_much);
		TextView viewMethod = (TextView) view.findViewById(R.id.method);
		TextView viewNote = (TextView) view.findViewById(R.id.note);

		long when = c.getLong(idxWhen);
		viewWhen.setText(fmtWhen.format(new Date(when)));

		int howMuch = c.getInt(idxHowMuch);
		viewHowMuch.setText(fmtHowMuch.format(howMuch));

		int method = c.getInt(idxMethod);
		viewMethod.setText(fmtMethod.format(method));

		String note = c.getString(idxNote);
		viewNote.setText(note);

	}

}
