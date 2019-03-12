/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.AnnotatedType;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;
import com.sun.javadoc.TypeVariable;
import com.sun.javadoc.WildcardType;

/**
 * @author dsporn
 *
 */
public class TypeMock extends TypeMockModel implements Type
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asAnnotatedType()
	 */
	@Override
	public AnnotatedType asAnnotatedType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asAnnotationTypeDoc()
	 */
	@Override
	public AnnotationTypeDoc asAnnotationTypeDoc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asClassDoc()
	 */
	@Override
	public ClassDoc asClassDoc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asParameterizedType()
	 */
	@Override
	public ParameterizedType asParameterizedType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asTypeVariable()
	 */
	@Override
	public TypeVariable asTypeVariable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#asWildcardType()
	 */
	@Override
	public WildcardType asWildcardType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#dimension()
	 */
	@Override
	public String dimension()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#getElementType()
	 */
	@Override
	public Type getElementType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#isPrimitive()
	 */
	@Override
	public boolean isPrimitive()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#qualifiedTypeName()
	 */
	@Override
	public String qualifiedTypeName()
	{
		return getValQualifiedName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#simpleTypeName()
	 */
	@Override
	public String simpleTypeName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Type#typeName()
	 */
	@Override
	public String typeName()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
