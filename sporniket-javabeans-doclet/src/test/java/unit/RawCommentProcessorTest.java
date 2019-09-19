/**
 * 
 */
package unit;

import static java.lang.String.join;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sporniket.libre.javabeans.doclet.DocletOptions;
import com.sporniket.libre.javabeans.doclet.RawCommentProcessorFactory;

import test.sporniket.libre.javabeans.doclet.TestBase;

/**
 * Unit tests on RawCommentProcessors created by {@link RawCommentProcessorFactory}.
 * 
 * @author dsporn
 *
 */
public class RawCommentProcessorTest extends TestBase
{
	public static class TestSpec
	{
		public String description;

		public String[] input;

		public String[] expected;
	}

	public static class TestSuite
	{
		public List<TestSpec> specs;
	}

	@TestFactory
	public Stream<DynamicTest> shouldConvertAsExpected() throws JsonParseException, JsonMappingException, IOException
	{
		// prepare
		TestSuite _suite = loadJsonData(getPathToDataRessource("testSuite.json"), TestSuite.class);
		final Function<String, String> _processor = RawCommentProcessorFactory.createRawCommentProcessor(new DocletOptions());

		// execute and verify
		return _suite.specs.parallelStream()//
				.map(s -> dynamicTest(//
						s.description, //
						() -> {
							then(_processor.apply(join("\n", s.input))).isEqualTo(join("\n", s.expected));
						}));
	}

}
