/**
 * 
 */
package test.sporniket.libre.javabeans.doclet;

import static com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor.ExtractionMode.EXPANDER;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sporniket.libre.javabeans.doclet.CodeSpecsExtractor;
import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.codespecs.ClassSpecs;
import com.sporniket.libre.javabeans.doclet.codespecs.ImportSpecs;

import test.sporniket.libre.javabeans.mockjavadoc.MockSetup;
import test.sporniket.libre.javabeans.mockjavadoc.MockSetupLoader;

/**
 * Tests for {@link CodeSpecsExtractor} when expanding pojos.
 * 
 * @author dsporn
 *
 */
public class CodeSpecsExtractorExpanderTest extends TestBase
{
	@TestFactory
	public Stream<DynamicTest> shouldHaveExpectedImportsSpecs() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare
		MockSetup setup = new MockSetupLoader().load(getDataRessource("classWithInheritedField.json"));

		// execute
		ClassSpecs _classSpecs = new CodeSpecsExtractor().extractSpecs(setup.getClasses().get("TheClass"),
				new HashMap<String, String>(0), new DocletOptions(), EXPANDER);

		// verify
		return asList(//
				dynamicTest("Should not miss an import"//
						, () -> then(_classSpecs.getImports()//
								.stream()//
								.map(ImportSpecs::getClassName)//
								.collect(toList())//
						)//
								.contains("java.net.URL", "java.util.Date", "TheClass", "TheBaseClass")//
								.hasSize(4)),
				dynamicTest("Should not import directly types from inherited fields"//
						, () -> then(_classSpecs.getImports()//
								.stream()//
								.filter(ImportSpecs::isDirectlyRequired)//
								.map(ImportSpecs::getClassName)//
								.collect(toList())//
						)//
								.doesNotContain("java.util.Date")//
								.hasSize(3))//
		).stream();
	}
}
