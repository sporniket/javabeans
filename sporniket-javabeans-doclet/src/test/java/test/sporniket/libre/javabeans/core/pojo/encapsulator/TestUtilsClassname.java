package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.libre.javabeans.core.pojo.encapsulator.UtilsClassname;

public class TestUtilsClassname
{
	@Test
	public void test__getPackageName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getPackageName(Object.class.getName());
		// verify
		assertThat(_toTest, is(Object.class.getPackage().getName()));
	}
	@Test
	public void test__getSimpleName()
	{
		// prepare
		// execute
		String _toTest = UtilsClassname.getSimpleName(Object.class.getName());
		// verify
		assertThat(_toTest, is(Object.class.getSimpleName()));
	}
}
