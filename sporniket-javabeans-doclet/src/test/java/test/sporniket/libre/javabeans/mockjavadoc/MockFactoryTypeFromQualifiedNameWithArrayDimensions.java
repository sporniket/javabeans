/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.Type;

/**
 * Factory to quickly setup a {@link Type} with a qualified name and optionnal array dimensions markers (one "[]" sequence by
 * dimension).
 * 
 * @author dsporn
 *
 */
class MockFactoryTypeFromQualifiedNameWithArrayDimensions implements MockFactory<String, Type>
{
	private final static String ARRAY_DIMENSION_MARK = "[]";

	@Override
	public Type build(String input)
	{
		// separation of the type qualified name and the array dimension markers.
		String _qualifiedName = input;
		StringBuilder _arrayDimensionMarks = new StringBuilder();
		while (_qualifiedName.endsWith(ARRAY_DIMENSION_MARK))
		{
			_arrayDimensionMarks.append(ARRAY_DIMENSION_MARK);
			_qualifiedName = _qualifiedName.substring(0, _qualifiedName.length() - ARRAY_DIMENSION_MARK.length());
		}

		// done
		return (Type) new TypeMockModel_Builder(new TypeMock())//
				.withValQualifiedName(_qualifiedName)//
				.withValDimension(_arrayDimensionMarks.toString())//
				.done();
	}

}
