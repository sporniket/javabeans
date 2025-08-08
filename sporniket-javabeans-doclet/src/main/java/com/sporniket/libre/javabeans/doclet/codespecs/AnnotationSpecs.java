package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

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

