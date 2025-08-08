package com.sporniket.libre.javabeans.doclet.codespecs;

import java.util.List;

public class AnnotationParameterSpecsValuesArray 
        extends AnnotationParameterSpecs
{

    private List<AnnotationParameterSpecsSingleValue> myValues ;


    public List<AnnotationParameterSpecsSingleValue> getValues() {return myValues ;}
    public void setValues(List<AnnotationParameterSpecsSingleValue> value) {myValues = value;}

}

