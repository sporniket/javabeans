/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SerialFieldTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

/**
 * Mocked {@link FieldDoc}.
 * 
 * @author dsporn
 *
 */
public class FieldDocMock extends FieldDocMockModel implements FieldDoc
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.MemberDoc#isSynthetic()
	 */
	@Override
	public boolean isSynthetic()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#annotations()
	 */
	@Override
	public AnnotationDesc[] annotations()
	{
		// TODO Auto-generated method stub
		return new AnnotationDesc[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#containingClass()
	 */
	@Override
	public ClassDoc containingClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#containingPackage()
	 */
	@Override
	public PackageDoc containingPackage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isFinal()
	 */
	@Override
	public boolean isFinal()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPackagePrivate()
	 */
	@Override
	public boolean isPackagePrivate()
	{
		return isValIsPackagePrivate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPrivate()
	 */
	@Override
	public boolean isPrivate()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isProtected()
	 */
	@Override
	public boolean isProtected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isPublic()
	 */
	@Override
	public boolean isPublic()
	{
		return isValIsPublic();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#isStatic()
	 */
	@Override
	public boolean isStatic()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#modifierSpecifier()
	 */
	@Override
	public int modifierSpecifier()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#modifiers()
	 */
	@Override
	public String modifiers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.ProgramElementDoc#qualifiedName()
	 */
	@Override
	public String qualifiedName()
	{
		return getValQualifiedName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#commentText()
	 */
	@Override
	public String commentText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#firstSentenceTags()
	 */
	@Override
	public Tag[] firstSentenceTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#getRawCommentText()
	 */
	@Override
	public String getRawCommentText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#inlineTags()
	 */
	@Override
	public Tag[] inlineTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isAnnotationType()
	 */
	@Override
	public boolean isAnnotationType()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isAnnotationTypeElement()
	 */
	@Override
	public boolean isAnnotationTypeElement()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isClass()
	 */
	@Override
	public boolean isClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isConstructor()
	 */
	@Override
	public boolean isConstructor()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isEnum()
	 */
	@Override
	public boolean isEnum()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isEnumConstant()
	 */
	@Override
	public boolean isEnumConstant()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isError()
	 */
	@Override
	public boolean isError()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isException()
	 */
	@Override
	public boolean isException()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isField()
	 */
	@Override
	public boolean isField()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isIncluded()
	 */
	@Override
	public boolean isIncluded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isInterface()
	 */
	@Override
	public boolean isInterface()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isMethod()
	 */
	@Override
	public boolean isMethod()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#isOrdinaryClass()
	 */
	@Override
	public boolean isOrdinaryClass()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#name()
	 */
	@Override
	public String name()
	{
		return getValName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#position()
	 */
	@Override
	public SourcePosition position()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#seeTags()
	 */
	@Override
	public SeeTag[] seeTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#setRawCommentText(java.lang.String)
	 */
	@Override
	public void setRawCommentText(String arg0)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#tags()
	 */
	@Override
	public Tag[] tags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.Doc#tags(java.lang.String)
	 */
	@Override
	public Tag[] tags(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#constantValue()
	 */
	@Override
	public Object constantValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#constantValueExpression()
	 */
	@Override
	public String constantValueExpression()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#isTransient()
	 */
	@Override
	public boolean isTransient()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#isVolatile()
	 */
	@Override
	public boolean isVolatile()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#serialFieldTags()
	 */
	@Override
	public SerialFieldTag[] serialFieldTags()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.FieldDoc#type()
	 */
	@Override
	public Type type()
	{
		return getValType();
	}

}
