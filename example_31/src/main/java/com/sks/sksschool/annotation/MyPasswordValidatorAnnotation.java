package com.sks.sksschool.annotation;

import com.sks.sksschool.validation.MyPasswordStrengthValidator;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/*See MyFieldValueMatchAnnotation class for explanation */
@Documented
@Constraint(validatedBy = {MyPasswordStrengthValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface MyPasswordValidatorAnnotation {

    String message() default "{Please Choose Strong Password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
