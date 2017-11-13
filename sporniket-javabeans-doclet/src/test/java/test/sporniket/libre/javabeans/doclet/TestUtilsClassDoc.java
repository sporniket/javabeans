package test.sporniket.libre.javabeans.doclet;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sporniket.libre.javabeans.doclet.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sun.javadoc.ClassDoc;

@RunWith(MockitoJUnitRunner.class)
public class TestUtilsClassDoc
{
	@Mock
	ClassDoc class1;

	@Test
	public void testUpdateKnownClasses()
	{
		// prepare
		List<ImportSpecs> _result = new ArrayList<>(10);

		// execute
		UtilsClassDoc.updateKnownClasses(_result, class1);

		// verify
		assertThat(_result.size(), is(2));
		assertThat(_result.stream().filter(i->Boolean.TRUE.equals(i.getDirectlyRequired())).map(ImportSpecs::getClassName).collect(toList()), CoreMatchers.hasItem(URL.class.getName())) ;
		assertThat(_result.stream().filter(i->Boolean.FALSE.equals(i.getDirectlyRequired())).map(ImportSpecs::getClassName).collect(toList()), CoreMatchers.hasItem(Date.class.getName())) ;
	}
}
