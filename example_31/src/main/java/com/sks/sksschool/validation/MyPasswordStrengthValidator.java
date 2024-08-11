package com.sks.sksschool.validation;

import com.sks.sksschool.annotation.MyPasswordValidatorAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/*See MyFieldValueMatchValidator class for explanation*/
public class MyPasswordStrengthValidator implements ConstraintValidator<MyPasswordValidatorAnnotation,String> {

    List<String> weakPasswords;
    @Override
    public void initialize(MyPasswordValidatorAnnotation constraintAnnotation) {
        weakPasswords= Arrays.asList("12345", "qwerty", "password");
    }

    @Override
    public boolean isValid(String pwdField, ConstraintValidatorContext constraintValidatorContext) {
        return pwdField!=null && !weakPasswords.contains(pwdField);
    }
}
