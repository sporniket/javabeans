/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import java.util.Map;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

/**
 * Sets of mocked {@link ClassDoc}, {@link FieldDoc} and {@link Type}, each mock is accessible through a qualified name.
 * 
 * @author dsporn
 *
 */
public class MockSetup_Raw
{
	Map<String, ClassDoc> classes;

	Map<String, FieldDoc> fields;

	Map<String, PackageDoc> packages;

	Map<String, Type> types;
}
