package com.sks.sksschool.validation;

import com.sks.sksschool.annotation.MyFieldValueMatchAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

/*We have created this Validator class which implemented ConstraintValidator passing MyFieldValueMatchAnnotation
* and the type of class. As we are passing full Person class so we are using Object here.*/
public class MyFieldValueMatchValidator implements ConstraintValidator<MyFieldValueMatchAnnotation,Object> {

    private String firstField;
    private String secondField;

    /*Two methods need to override. here in initialize method we are giving the field name in this local
    * variables*/
    @Override
    public void initialize(MyFieldValueMatchAnnotation constraintAnnotation) {

        this.firstField=constraintAnnotation.field1();
        this.secondField=constraintAnnotation.field2();
    }

    /*Getting property values and then comparing. If it's same then return true nor false.
    * Here in Object value has Person Object */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

       Object firstFieldValue=new BeanWrapperImpl(value).getPropertyValue(firstField);
       Object secondFieldValue= new BeanWrapperImpl(value).getPropertyValue(secondField);

       if(firstFieldValue !=null){
           return firstFieldValue.equals(secondFieldValue);
       }else{
           return false;
       }
    }
}
