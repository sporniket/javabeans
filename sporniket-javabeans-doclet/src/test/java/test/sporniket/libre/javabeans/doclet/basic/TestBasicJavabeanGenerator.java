/**
 * 
 */
package test.sporniket.libre.javabeans.doclet.basic;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.basic.BasicJavabeanGenerator;
import com.sporniket.libre.javabeans.doclet.basic.Builder;
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
 * This file is part of <i>The Sporniket Javabeans Library &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Library &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
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
 * @version 17.09.01
 * @since 17.09.01
 */
@RunWith(MockitoJUnitRunner.class)
public class TestBasicJavabeanGenerator
{
	@Mock
	DocletOptions options;

	@Mock
	FieldDoc field1;

	@Mock
	ClassDoc class1;
	
	@Mock Type type1 ;

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsEmpty() throws UnsupportedEncodingException
	{
		// prepare
		Mockito.when(options.getBeanFieldPrefix()).thenReturn("");
		Mockito.when(field1.name()).thenReturn("theField");
		Mockito.when(field1.isPackagePrivate()).thenReturn(true);
		Mockito.when(field1.type()).thenReturn(type1);
		Mockito.when(type1.qualifiedTypeName()).thenReturn("foo");
		// * class hierarchy - class1
		Mockito.when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});

		final Charset _charset = StandardCharsets.UTF_8;
		ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		BasicJavabeanGenerator _generator = new Builder<BasicJavabeanGenerator>(new BasicJavabeanGenerator())
				.withKnownClasses(new HashSet<>()).withShortables(new HashSet<>()).withOptions(options)
				.withTranslations(new HashMap<>()).withOut(new PrintStream(_ps)).withSource(class1).done();
		// execute
		_generator.outputAccessors();
		String _result = new String(_baos.toByteArray(), _charset);

		// verify
		Assert.assertThat(_result, CoreMatchers.containsString("public foo getTheField("));
		Assert.assertThat(_result, CoreMatchers.containsString("public void setTheField("));
		Assert.assertThat(_result, CoreMatchers.containsString("{return this.theField ;}"));
		Assert.assertThat(_result, CoreMatchers.containsString("{this.theField = value;}"));
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see https://github.com/sporniket/javabeans/issues/19
	 */
	@Test
	public void testOutputAccessorsWhenBeanFieldPrefixIsSpecified() throws UnsupportedEncodingException
	{
		// prepare
		Mockito.when(options.getBeanFieldPrefix()).thenReturn("my");
		Mockito.when(field1.name()).thenReturn("theField");
		Mockito.when(field1.isPackagePrivate()).thenReturn(true);
		Mockito.when(field1.type()).thenReturn(type1);
		Mockito.when(type1.qualifiedTypeName()).thenReturn("foo");
		// * class hierarchy - class1
		Mockito.when(class1.fields()).thenReturn(new FieldDoc[]
		{
				field1
		});

		final Charset _charset = StandardCharsets.UTF_8;
		ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		PrintStream _ps = new PrintStream(_baos, true, _charset.name());
		BasicJavabeanGenerator _generator = new Builder<BasicJavabeanGenerator>(new BasicJavabeanGenerator())
				.withKnownClasses(new HashSet<>()).withShortables(new HashSet<>()).withOptions(options)
				.withTranslations(new HashMap<>()).withOut(new PrintStream(_ps)).withSource(class1).done();
		// execute
		_generator.outputAccessors();
		String _result = new String(_baos.toByteArray(), _charset);

		// verify
		Assert.assertThat(_result, CoreMatchers.containsString("public foo getTheField("));
		Assert.assertThat(_result, CoreMatchers.containsString("public void setTheField("));
		Assert.assertThat(_result, CoreMatchers.containsString("{return myTheField ;}"));
		Assert.assertThat(_result, CoreMatchers.containsString("{myTheField = value;}"));
	}
}
