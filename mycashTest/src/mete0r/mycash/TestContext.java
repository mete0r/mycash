package mete0r.mycash;

import junit.framework.TestCase;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

public class TestContext extends RenamingDelegatingContext {

	public TestContext(Context context, TestCase test) {
		super(context, test.getName() + '.');
	}

	public TestContext(InstrumentationTestCase test) {
		this(test.getInstrumentation().getTargetContext(), test);
	}

}
