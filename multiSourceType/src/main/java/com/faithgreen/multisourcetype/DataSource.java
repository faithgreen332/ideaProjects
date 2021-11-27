package com.faithgreen.multisourcetype;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    MultiDataSourceTypeEnum value() default MultiDataSourceTypeEnum.LOCAL;
}
