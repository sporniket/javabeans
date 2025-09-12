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
public class AnnotationSpecs 
{

    private boolean myOnBuilder ;

    private boolean myOnField ;

    private boolean myOnGetter ;

    private boolean myOnSetter ;

    private List<AnnotationParameterSpecs> myParameters ;

    private String myType ;


    public boolean isOnBuilder() {return myOnBuilder ;}
    public void setOnBuilder(boolean value) {myOnBuilder = value;}

    public boolean isOnField() {return myOnField ;}
    public void setOnField(boolean value) {myOnField = value;}

    public boolean isOnGetter() {return myOnGetter ;}
    public void setOnGetter(boolean value) {myOnGetter = value;}

    public boolean isOnSetter() {return myOnSetter ;}
    public void setOnSetter(boolean value) {myOnSetter = value;}

    public List<AnnotationParameterSpecs> getParameters() {return myParameters ;}
    public void setParameters(List<AnnotationParameterSpecs> value) {myParameters = value;}

    public String getType() {return myType ;}
    public void setType(String value) {myType = value;}

}

