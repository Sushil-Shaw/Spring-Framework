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

       /*Here Spring MVC is also calling to compare the email and pwd with confirmEmail and confirmPwd
       * and Spring JPA is also calling to compare. But now it'll fail, as we are changing the pwd
       * to Hashed format but not the confirmPwd. so this validation will fail and throw error.
       * So here are two options, first implemented below. Whenever we find the pwd starts with
       * $2a means it's hashed value, so return true. Or disable the Jpa validation in application.
       * properties file. spring. jpa.properties.javax.persistence.validation.mode=none.
       * I'm adding this properties file hence commenting below code.*/
       if(firstFieldValue !=null){
           /*if(firstFieldValue.toString().startsWith("$2a")){
               return true;
           }else {*/
               return firstFieldValue.equals(secondFieldValue);
       }else{
           return false;
       }
    }
}
