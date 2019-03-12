/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

import test.sporniket.libre.javabeans.doclet.TestBase;

/**
 * Tests for {@link MockSetupLoader}.
 * 
 * @author dsporn
 *
 */
public class MockSetupLoaderTest extends TestBase
{
	@Test
	public void shouldBuildWorkingSetup() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare

		// execute
		MockSetup setup = new MockSetupLoader().load(getDataRessource("classWithInheritedField.json"));

		// verify
		// -- overall checks
		then(setup.getTypes())//
				.hasSize(2)//
				.containsKeys("java.net.URL", "java.util.Date");
		setup.getTypes().forEach((n, t) -> {
			then(t.qualifiedTypeName()).isEqualTo(n);
		});

		then(setup.getFields())//
				.hasSize(2)//
				.containsKeys("TheBaseClass.myDateCreation", "TheClass.mySource");
		setup.getFields().forEach((n, f) -> {
			then(f.qualifiedName()).isEqualTo(n);
		});

		then(setup.getClasses())//
				.hasSize(3)//
				.containsKeys("TheClass", "TheBaseClass", "java.lang.Object");
		setup.getClasses().forEach((n, c) -> {
			then(c.qualifiedName()).isEqualTo(n);
		});

		then(setup.getPackages())//
				.hasSize(1)//
				.containsKeys("");
		setup.getPackages().forEach((n, p) -> {
			then(p.name()).isEqualTo(n);
		});

		// -- field details
		thenFieldHasExpectedFeatures(setup.getFields().get("TheBaseClass.myDateCreation")//
				, "myDateCreation", setup.getTypes().get("java.util.Date")//
				, true//
				, false);
		thenFieldHasExpectedFeatures(setup.getFields().get("TheClass.mySource")//
				, "mySource", setup.getTypes().get("java.net.URL")//
				, true//
				, false);

		// -- class details
		thenClassDocHasExpectedFeatures(setup.getClasses().get("TheBaseClass")//
				, new FieldDoc[]
				{
						setup.getFields().get("TheBaseClass.myDateCreation")
				}//
				, setup.getClasses().get("java.lang.Object")//
				, setup.getPackages().get(""));
		thenClassDocHasExpectedFeatures(setup.getClasses().get("TheClass")//
				, new FieldDoc[]
				{
						setup.getFields().get("TheClass.mySource")//
						, setup.getFields().get("TheBaseClass.myDateCreation")
				}//
				, setup.getClasses().get("TheBaseClass")//
				, setup.getPackages().get(""));

	}

	private void thenFieldHasExpectedFeatures(FieldDoc field, String name, Type type, boolean isPackagePrivate, boolean isPublic)
	{
		then(field.name()).isEqualTo(name);
		then(field.type()).isEqualTo(type);
		then(field.isPackagePrivate()).isTrue();
		then(field.isPublic()).isFalse();
	}

	private void thenClassDocHasExpectedFeatures(ClassDoc classdoc, FieldDoc[] fields, ClassDoc superclass,
			PackageDoc containingPackage)
	{
		then(classdoc.fields())//
				.hasSize(fields.length)//
				.contains(fields);
		then(classdoc.superclass()).isEqualTo(superclass);
		then(classdoc.containingPackage()).isEqualTo(containingPackage);
	}
}
