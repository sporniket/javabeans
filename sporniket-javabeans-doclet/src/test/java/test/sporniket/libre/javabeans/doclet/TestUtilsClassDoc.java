package test.sporniket.libre.javabeans.doclet;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sporniket.libre.javabeans.doclet.UtilsClassDoc;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;

@RunWith(MockitoJUnitRunner.class)
public class TestUtilsClassDoc
{
	@Mock
	ClassDoc class1;

	@Mock
	ClassDoc class2;

	@Mock
	ClassDoc class3;

	@Mock
	FieldDoc field1;

	@Mock
	FieldDoc field2;

	@Mock
	Type type1;

	@Mock
	Type type2;

	@Test
	public void testUpdateKnownClasses__doNotMissAnImport()
	{
		// prepare
		final String _thisClassName = "test.class.this";
		Mockito.when(class1.qualifiedName()).thenReturn(_thisClassName);
		Mockito.when(class1.superclass()).thenReturn(class2);
		Mockito.when(class1.interfaces()).thenReturn(new ClassDoc[] {});
		Mockito.when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});

		final String _superClassName = "test.class.super";
		Mockito.when(class2.qualifiedName()).thenReturn(_superClassName);
		Mockito.when(class2.superclass()).thenReturn(class3);
		Mockito.when(class2.fields()).thenReturn(new FieldDoc[]
		{
				field2
		});

		Mockito.when(class3.qualifiedName()).thenReturn(Object.class.getName());

		Mockito.when(field1.isPublic()).thenReturn(false);
		Mockito.when(field1.isPackagePrivate()).thenReturn(true);
		Mockito.when(field1.type()).thenReturn(type1);

		Mockito.when(field2.isPublic()).thenReturn(true);
		Mockito.when(field2.type()).thenReturn(type2);

		Mockito.when(type1.qualifiedTypeName()).thenReturn(URL.class.getName());

		Mockito.when(type2.qualifiedTypeName()).thenReturn(Date.class.getName());

		// execute
		Collection<ImportSpecs> _result = UtilsClassDoc.updateKnownClasses(class1);

		// verify
		assertThat(_result.size(), is(4));

		final List<String> _imports = _result.stream()//
				.map(ImportSpecs::getClassName)//
				.collect(toList());
		assertThat(_imports, CoreMatchers.hasItem(URL.class.getName()));
		assertThat(_imports, CoreMatchers.hasItem(_superClassName));
		assertThat(_imports, CoreMatchers.hasItem(_thisClassName));
		assertThat(_imports, CoreMatchers.hasItem(Date.class.getName()));
	}

	@Test
	public void testUpdateKnownClasses__inheritedPublicFieldsAreNotDirectImports()
	{
		// prepare
		final String _thisClassName = "test.class.this";
		Mockito.when(class1.qualifiedName()).thenReturn(_thisClassName);
		Mockito.when(class1.superclass()).thenReturn(class2);
		Mockito.when(class1.interfaces()).thenReturn(new ClassDoc[] {});
		Mockito.when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});

		final String _superClassName = "test.class.super";
		Mockito.when(class2.qualifiedName()).thenReturn(_superClassName);
		Mockito.when(class2.superclass()).thenReturn(class3);
		Mockito.when(class2.fields()).thenReturn(new FieldDoc[]
		{
				field2
		});

		Mockito.when(class3.qualifiedName()).thenReturn(Object.class.getName());

		Mockito.when(field1.isPublic()).thenReturn(false);
		Mockito.when(field1.isPackagePrivate()).thenReturn(true);
		Mockito.when(field1.type()).thenReturn(type1);

		Mockito.when(field2.isPublic()).thenReturn(true);
		Mockito.when(field2.type()).thenReturn(type2);

		Mockito.when(type1.qualifiedTypeName()).thenReturn(URL.class.getName());

		Mockito.when(type2.qualifiedTypeName()).thenReturn(Date.class.getName());

		// execute
		Collection<ImportSpecs> _result = UtilsClassDoc.updateKnownClasses(class1);

		// verify
		assertThat(_result.isEmpty(), is(false));

		assertThat(_result.stream().filter(i -> Boolean.FALSE.equals(i.getDirectlyRequired())).map(ImportSpecs::getClassName)
				.collect(toList()), CoreMatchers.hasItem(Date.class.getName()));
	}
}
