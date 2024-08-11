package com.sks.sksschool.annotation;

import com.sks.sksschool.validation.MyFieldValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/*Here we are creating new annotation for checking two fields are matching. Check @Email annotation
from jakarta.validations.constraints package
* @Documented means we want this annotation to be documented as well for any java documentation
@Constraint here we are passing this implementation class of this annotation
@Retention Runtime means we want this available in runtime only
@target is Type means we want this annotation to be used in Class Type only, not in any method etc,*/
@Documented
@Constraint(validatedBy = {MyFieldValueMatchValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyFieldValueMatchAnnotation {

    /* default message*/
    String message() default "{Fields values are not matching}";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*field1 anf field2*/
    String field1();
    String field2();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
     @interface List {
        MyFieldValueMatchAnnotation[] value();
    }

}
