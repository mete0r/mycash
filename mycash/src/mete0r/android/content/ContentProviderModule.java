package mete0r.android.content;



public abstract class ContentProviderModule {

	public final String subtype;

	public ContentProviderModule(String subtype) {
		this.subtype = subtype;
	}

	public abstract ContentDirectory directory();

	public ContentCRUD crud() {
		return new ContentCrudOnDirectory(directory());
	}

}
