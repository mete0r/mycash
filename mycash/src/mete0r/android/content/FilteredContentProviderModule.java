package mete0r.android.content;


public class FilteredContentProviderModule extends ContentProviderModuleWrapper {

	ContentFilter filter;

	public FilteredContentProviderModule(ContentProviderModule underlying,
			ContentFilter filter) {
		super(underlying);
		this.filter = filter;
	}

	public ContentFilter filter() {
		return filter;
	}

	public void filter(ContentFilter filter) {
		this.filter = filter;
	}

	@Override
	public ContentDirectory directory() {
		ContentDirectory directory = super.directory();
		FilteredContentDirectory fcd = new FilteredContentDirectory(directory);
		fcd.filter(filter);
		return fcd;
	}

	@Override
	public ContentCRUD crud() {
		ContentCRUD crud = super.crud();
		FilteredContentCRUD fcrud = new FilteredContentCRUD();
		fcrud.underlying(crud);
		fcrud.filter(filter);
		return fcrud;
	}

}
