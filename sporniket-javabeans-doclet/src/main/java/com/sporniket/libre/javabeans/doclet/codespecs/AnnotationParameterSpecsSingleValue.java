package com.sporniket.libre.javabeans.doclet.codespecs;


public class AnnotationParameterSpecsSingleValue 
        extends AnnotationParameterSpecs
{

    private boolean myString ;

    private Object myValue ;


    public boolean isString() {return myString ;}
    public void setString(boolean value) {myString = value;}

    public Object getValue() {return myValue ;}
    public void setValue(Object value) {myValue = value;}

}

