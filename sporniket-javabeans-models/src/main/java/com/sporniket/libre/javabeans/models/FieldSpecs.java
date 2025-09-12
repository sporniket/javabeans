package com.sporniket.libre.javabeans.models;

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
public class FieldSpecs 
{

    private List<AnnotationSpecs> myAnnotations ;

    private String myArrayMarker ;

    private boolean myBooleanGetter ;

    private boolean myDirectlyRequired ;

    private String myFieldPrefix ;

    private String[] myJavadocLines ;

    private String myNameForAccessor ;

    private String myNameForField ;

    private String myTypeInvocation ;


    public List<AnnotationSpecs> getAnnotations() {return myAnnotations ;}
    public void setAnnotations(List<AnnotationSpecs> value) {myAnnotations = value;}

    public String getArrayMarker() {return myArrayMarker ;}
    public void setArrayMarker(String value) {myArrayMarker = value;}

    public boolean isBooleanGetter() {return myBooleanGetter ;}
    public void setBooleanGetter(boolean value) {myBooleanGetter = value;}

    public boolean isDirectlyRequired() {return myDirectlyRequired ;}
    public void setDirectlyRequired(boolean value) {myDirectlyRequired = value;}

    public String getFieldPrefix() {return myFieldPrefix ;}
    public void setFieldPrefix(String value) {myFieldPrefix = value;}

    public String[] getJavadocLines() {return myJavadocLines ;}
    public void setJavadocLines(String[] value) {myJavadocLines = value;}

    public String getNameForAccessor() {return myNameForAccessor ;}
    public void setNameForAccessor(String value) {myNameForAccessor = value;}

    public String getNameForField() {return myNameForField ;}
    public void setNameForField(String value) {myNameForField = value;}

    public String getTypeInvocation() {return myTypeInvocation ;}
    public void setTypeInvocation(String value) {myTypeInvocation = value;}

}

