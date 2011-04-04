package mete0r.mycash.content;

import android.net.Uri;

public interface EventContent extends EventColumns {
	public static String CONTENT_PATH = "Event";
	public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
			.authority(Contents.AUTHORITY).path(CONTENT_PATH).build();
	public static String CONTENT_SUBTYPE = "vnd.mete0r.mycash.event";
}
