package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

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

