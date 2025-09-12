package com.sporniket.libre.javabeans.models;

import java.util.Collection;
import java.util.List;

/** GENERATED CODE !
 * <p>
 * &copy; Copyright 2012-2025 David Sporn
 * </p>
 * <hr>
 *
 * <p>
 * This file is part of <i>The Sporniket Javabeans Project &#8211; doclet</i>.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * <p>
 * <i>The Sporniket Javabeans Project &#8211; doclet</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Javabeans Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 *
 * <hr>
 *
 * @author David SPORN
 * @version 25.11.00
 * @since 25.11.00
 */
public class ClassSpecs 
{

    private boolean myAbstractRequired ;

    private List<AnnotationSpecs> myAnnotations ;

    private String myClassName ;

    private String myClassNameFullyQualified ;

    private String myClassNameOutput ;

    private String myDeclaredTypeArguments ;

    private List<FieldSpecs> myFields ;

    private Collection<ImportSpecs> myImports ;

    private String myInterfaceList ;

    private String myInvokedTypeArguments ;

    private String[] myJavadocLines ;

    private String myPackageName ;

    private String mySuperClassName ;


    public boolean isAbstractRequired() {return myAbstractRequired ;}
    public void setAbstractRequired(boolean value) {myAbstractRequired = value;}

    public List<AnnotationSpecs> getAnnotations() {return myAnnotations ;}
    public void setAnnotations(List<AnnotationSpecs> value) {myAnnotations = value;}

    public String getClassName() {return myClassName ;}
    public void setClassName(String value) {myClassName = value;}

    public String getClassNameFullyQualified() {return myClassNameFullyQualified ;}
    public void setClassNameFullyQualified(String value) {myClassNameFullyQualified = value;}

    public String getClassNameOutput() {return myClassNameOutput ;}
    public void setClassNameOutput(String value) {myClassNameOutput = value;}

    public String getDeclaredTypeArguments() {return myDeclaredTypeArguments ;}
    public void setDeclaredTypeArguments(String value) {myDeclaredTypeArguments = value;}

    public List<FieldSpecs> getFields() {return myFields ;}
    public void setFields(List<FieldSpecs> value) {myFields = value;}

    public Collection<ImportSpecs> getImports() {return myImports ;}
    public void setImports(Collection<ImportSpecs> value) {myImports = value;}

    public String getInterfaceList() {return myInterfaceList ;}
    public void setInterfaceList(String value) {myInterfaceList = value;}

    public String getInvokedTypeArguments() {return myInvokedTypeArguments ;}
    public void setInvokedTypeArguments(String value) {myInvokedTypeArguments = value;}

    public String[] getJavadocLines() {return myJavadocLines ;}
    public void setJavadocLines(String[] value) {myJavadocLines = value;}

    public String getPackageName() {return myPackageName ;}
    public void setPackageName(String value) {myPackageName = value;}

    public String getSuperClassName() {return mySuperClassName ;}
    public void setSuperClassName(String value) {mySuperClassName = value;}

}

