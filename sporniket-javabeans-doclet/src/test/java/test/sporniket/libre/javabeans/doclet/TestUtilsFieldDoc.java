package test.sporniket.libre.javabeans.doclet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sporniket.libre.javabeans.doclet.UtilsFieldDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

@RunWith(MockitoJUnitRunner.class)
public class TestUtilsFieldDoc
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
	FieldDoc field3;

	@Mock
	FieldDoc field4;

	@Test
	public void test__getAccessibleDeclaredFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * field 2
		when(field2.isPublic()).thenReturn(false);
		when(field2.isPackagePrivate()).thenReturn(true);

		// * field 3
		when(field3.isPublic()).thenReturn(false);
		when(field3.isPackagePrivate()).thenReturn(false);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1,
				field2,
				field3
		});

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleDeclaredFields(class1);

		// verify
		assertThat(_toTest, hasItem(field1));
		assertThat(_toTest, hasItem(field2));
		assertThat(_toTest, not(hasItem(field3)));
	}

	@Test
	public void test__getAccessibleFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * field 2
		when(field2.isPublic()).thenReturn(false);
		when(field2.isPackagePrivate()).thenReturn(true);

		// * field 3
		when(field3.isPublic()).thenReturn(false);
		when(field3.isPackagePrivate()).thenReturn(false);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1,
				field2,
				field3
		});
		when(class1.superclass()).thenReturn(class2);

		// * class 2
		when(class2.qualifiedName()).thenReturn(Object.class.getName());

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleFields(class1);

		// verify
		Mockito.verify(class2, Mockito.never()).fields();
		assertThat(_toTest, hasItem(field1));
		assertThat(_toTest, hasItem(field2));
		assertThat(_toTest, not(hasItem(field3)));
	}

	@Test
	public void test__getAccessibleFields__shouldIncludeInheritedFields()
	{
		// prepare
		// * field 1
		when(field1.isPublic()).thenReturn(true);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[] {});
		when(class1.superclass()).thenReturn(class2);

		// * class 2
		when(class2.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});
		when(class2.superclass()).thenReturn(class3);

		// * class 3
		when(class3.qualifiedName()).thenReturn(Object.class.getName());

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getAccessibleFields(class1);

		// verify
		assertThat(_toTest, hasItem(field1));
	}

	@Test
	public void test__getPrivateDeclaredFields()
	{
		// prepare
		// * field 1
		when(field1.isPrivate()).thenReturn(true);

		// * class 1
		when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1,
				field2
		});

		// execute
		List<FieldDoc> _toTest = UtilsFieldDoc.getPrivateDeclaredFields(class1);

		// verify
		assertThat(_toTest, hasItem(field1));
		assertThat(_toTest, not(hasItem(field2)));
	}
}
