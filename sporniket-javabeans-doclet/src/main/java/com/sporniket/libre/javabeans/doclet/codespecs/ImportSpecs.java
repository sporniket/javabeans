package com.sporniket.libre.javabeans.doclet.codespecs;


public class ImportSpecs 
{

    private boolean myAnnotation ;

    private String myClassName ;

    private boolean myDirectlyRequired ;


    public boolean isAnnotation() {return myAnnotation ;}
    public void setAnnotation(boolean value) {myAnnotation = value;}

    public String getClassName() {return myClassName ;}
    public void setClassName(String value) {myClassName = value;}

    public boolean isDirectlyRequired() {return myDirectlyRequired ;}
    public void setDirectlyRequired(boolean value) {myDirectlyRequired = value;}

}

