package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.Type;

/**
 * @author dsporn
 *
 * @param <InputType>
 * @param <MockType>
 */
interface MockFactory<InputType, MockType>
{

	/**
	 * Factory to quickly setup a {@link ClassDoc} with a qualified name.
	 */
	static MockFactory<String, ClassDoc> CLASSDOC_FROM_QUALIFIED_NAME = //
			qn -> (ClassDoc) new ClassDocMockModel_Builder(new ClassDocMock())//
					.withValQualifiedName(qn)//
					.done();

	/**
	 * Factory to quicly setup a {@link PackageDoc} with a qualified name.
	 */
	static MockFactory<String, PackageDoc> PACKAGEDOC_FROM_QUALIFIED_NAME = //
			qn -> (PackageDoc) new PackageDocMockModel_Builder(new PackageDocMock())//
					.withValQualifiedName(qn)//
					.done();

	/**
	 * Factory to quickly setup a {@link Type} with a qualified name.
	 */
	static MockFactory<String, Type> TYPE_FROM_QUALIFIED_NAME = //
			qn -> (Type) new TypeMockModel_Builder(new TypeMock())//
					.withValQualifiedName(qn)//
					.done();

	MockType build(InputType input);
}
