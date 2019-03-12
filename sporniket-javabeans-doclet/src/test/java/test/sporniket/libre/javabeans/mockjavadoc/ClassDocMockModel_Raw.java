/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import java.util.List;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;

/**
 * Model for a {@link ClassDocMock} .
 * 
 * @author dsporn
 *
 */
class ClassDocMockModel_Raw
{
	/**
	 * Names of fields
	 */
	List<String> refFields;

	/**
	 * Qualified names of implemented interfaces.
	 */
	List<String> refInterfaces;

	/**
	 * Qualified name of the superclass.
	 */
	String refSuperclass;

	/**
	 * Actual containing package.
	 */
	PackageDoc valContainingPackage;

	/**
	 * Actual fields.
	 */
	List<FieldDoc> valFieldDocs;

	/**
	 * Qualified name.
	 */
	String valQualifiedName;

	/**
	 * Actual superclass.
	 */
	ClassDoc valSuperclass;

}
