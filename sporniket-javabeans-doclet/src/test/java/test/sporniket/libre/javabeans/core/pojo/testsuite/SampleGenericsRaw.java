package test.sporniket.libre.javabeans.core.pojo.testsuite;

import java.util.Date;
import java.util.List;
import java.util.Map;

import test.sporniket.libre.javabeans.core.pojo.testsuite.SampleGenerics.Builder;

public class SampleGenericsRaw<T, R extends Number> extends SampleBasicRaw
{
    public static class Builder<T, R extends java.lang.Number>
    {
        private final SampleGenerics<T, R> bean = new SampleGenerics<>() ;

        public SampleGenerics<T, R> done() {return bean ;}

        public Builder withRegistry(Map<T, R> value) {bean.setRegistry(value); return this;}

        public Builder withDate(Date value) {bean.setDate(value); return this;}

        public Builder withNames(List<String> value) {bean.setNames(value); return this;}

    }

    public static <T, R extends Number> Builder<T, R> build() {return new Builder<T, R>() ;}
    
	Map<T, R> registry ;
}
