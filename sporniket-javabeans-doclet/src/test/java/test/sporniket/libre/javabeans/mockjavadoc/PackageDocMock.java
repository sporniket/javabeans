/**
 * 
 */
package test.sporniket.libre.javabeans.mockjavadoc;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

/**
 * Mocked {@link PackageDoc}.
 * 
 * @author dsporn
 *
 */
public class PackageDocMock extends PackageDocMockModel implements PackageDoc
{

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
		return getValQualifiedName();
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
	 * @see com.sun.javadoc.PackageDoc#allClasses()
	 */
	@Override
	public ClassDoc[] allClasses()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#allClasses(boolean)
	 */
	@Override
	public ClassDoc[] allClasses(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#annotationTypes()
	 */
	@Override
	public AnnotationTypeDoc[] annotationTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#annotations()
	 */
	@Override
	public AnnotationDesc[] annotations()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#enums()
	 */
	@Override
	public ClassDoc[] enums()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#errors()
	 */
	@Override
	public ClassDoc[] errors()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#exceptions()
	 */
	@Override
	public ClassDoc[] exceptions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#findClass(java.lang.String)
	 */
	@Override
	public ClassDoc findClass(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#interfaces()
	 */
	@Override
	public ClassDoc[] interfaces()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.javadoc.PackageDoc#ordinaryClasses()
	 */
	@Override
	public ClassDoc[] ordinaryClasses()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
