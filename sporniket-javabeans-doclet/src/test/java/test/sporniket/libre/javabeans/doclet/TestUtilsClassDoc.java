package test.sporniket.libre.javabeans.doclet;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

/**
 * <p>
 * &copy; Copyright 2012-2017 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 19.02.00
 * @since 17.12.00
 */
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

	private void doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(String primitive)
	{
		// prepare
		Mockito.when(class1.qualifiedName()).thenReturn("test.class.this");
		Mockito.when(class1.superclass()).thenReturn(class2);
		Mockito.when(class1.interfaces()).thenReturn(new ClassDoc[] {});
		Mockito.when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});

		Mockito.when(class2.qualifiedName()).thenReturn(Object.class.getName());

		Mockito.when(field1.isPublic()).thenReturn(true);
		Mockito.when(field1.type()).thenReturn(type1);

		Mockito.when(type1.qualifiedTypeName()).thenReturn(primitive);

		// execute
		List<String> _result = UtilsClassDoc.updateKnownClasses(class1)//
				.stream()//
				.map(ImportSpecs::getClassName)//
				.collect(toList());

		// verify
		assertThat(_result.isEmpty(), is(false));

		assertThat(_result, not(hasItem(primitive)));
	}

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
		assertThat(_imports, hasItem(URL.class.getName()));
		assertThat(_imports, hasItem(_superClassName));
		assertThat(_imports, hasItem(_thisClassName));
		assertThat(_imports, hasItem(Date.class.getName()));
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

		assertThat(_result.stream().filter(i -> Boolean.FALSE.equals(i.isDirectlyRequired())).map(ImportSpecs::getClassName)
				.collect(toList()), hasItem(Date.class.getName()));
	}

	@Test
	public void testUpdateKnownClasses__primitiveBooleanIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(boolean.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveByteIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(byte.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveCharIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(char.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveDoubleIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(double.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveFloatIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(float.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveIntIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(int.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveLongIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(long.class.getName());
	}

	@Test
	public void testUpdateKnownClasses__primitiveShortIsFilteredOut()
	{
		doTestUpdateKnownClasses__givenPrimitiveIsFilteredOut(short.class.getName());
	}
}
