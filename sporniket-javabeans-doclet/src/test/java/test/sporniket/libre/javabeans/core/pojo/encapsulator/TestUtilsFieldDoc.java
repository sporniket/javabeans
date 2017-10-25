package test.sporniket.libre.javabeans.core.pojo.encapsulator;

import static com.sporniket.libre.javabeans.doclet.UtilsFieldDoc.computeFieldAccessorSuffix;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sun.javadoc.FieldDoc;

@RunWith(MockitoJUnitRunner.class)
public class TestUtilsFieldDoc
{
	@Mock FieldDoc mainField ;
	
	@Test 
	public void test__computeFieldAccessorSuffix(){
		//prepare
		Mockito.when(mainField.name()).thenReturn("test");
		
		//execute
		String _toTest = computeFieldAccessorSuffix(mainField);
		
		//verify
		assertThat(_toTest, is("Test"));
	}
}
