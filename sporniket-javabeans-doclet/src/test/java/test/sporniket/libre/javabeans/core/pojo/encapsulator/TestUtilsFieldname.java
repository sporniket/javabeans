package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static com.sporniket.libre.javabeans.doclet.UtilsFieldname.computeFieldAccessorSuffix;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestUtilsFieldname
{
	@Test
	public void test__computeFieldAccessorSuffix()
	{
		// prepare
		// execute
		String _toTest = computeFieldAccessorSuffix("blah");
		// verify
		assertThat(_toTest, is("Blah"));
	}
}
