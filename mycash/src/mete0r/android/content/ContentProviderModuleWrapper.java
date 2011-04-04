package mete0r.android.content;


public class ContentProviderModuleWrapper extends ContentProviderModule {

	final ContentProviderModule underlying;

	public ContentProviderModuleWrapper(ContentProviderModule underlying) {
		super(underlying.subtype);
		this.underlying = underlying;
	}

	@Override
	public ContentDirectory directory() {
		return underlying.directory();
	}

	@Override
	public ContentCRUD crud() {
		return underlying.crud();
	}

}
