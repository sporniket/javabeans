/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sporniket.strings.pipeline.StringTransformation;

/**
 * @author dsporn
 *
 */
public class QualifiedNameUtils
{
	public static final StringTransformation CONTAINER_FROM_QUALIFIED_NAME = qn -> {
		final int _lastDotPos = qn.lastIndexOf('.');
		return -1 < _lastDotPos //
				? qn.substring(0, _lastDotPos) //
				: "";
	};

	public static final StringTransformation NAME_FROM_QUALIFIED_NAME = qn -> {
		final int _lastDotPos = qn.lastIndexOf('.');
		return -1 < _lastDotPos //
				? qn //
				: qn.substring(_lastDotPos + 1);
	};

}
