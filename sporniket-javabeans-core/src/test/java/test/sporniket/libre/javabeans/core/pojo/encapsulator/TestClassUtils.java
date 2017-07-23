package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.libre.javabeans.core.pojo.encapsulator.ClassUtils;

public class TestClassUtils
{
	@Test
	public void test__getPackageName()
	{
		// prepare
		// execute
		String _toTest = ClassUtils.getPackageName(Object.class.getName());
		// verify
		assertThat(_toTest, is(Object.class.getPackage().getName()));
	}
}
