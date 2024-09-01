package com.sks.sksschool.model;

import com.sks.sksschool.annotation.MyFieldValueMatchAnnotation;
import com.sks.sksschool.annotation.MyPasswordValidatorAnnotation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
/*Here we are using this annotation. We have created List inside this annotation so that we can use multiple
* first part, we are comparing pwd and confirmPwd and in second annotation, email and confirmEmail*/
@MyFieldValueMatchAnnotation.List({
        @MyFieldValueMatchAnnotation(
                field1 = "pwd",
                field2 = "confirmPwd",
                message = "Passwords do not match"
        ),
        @MyFieldValueMatchAnnotation(
                field1 = "email",
                field2 = "confirmEmail",
                message = "Emails do not match"
        ) }
)
public class Person extends MyBaseEntity{

    @Id
    /*Seq check. internally jpa will run select person_id_seq.nextVal from dual to get the next seq
    * and then it will insert the data with next number for that personId.*/
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "person_id_seq")
    @SequenceGenerator(name = "person_id_seq",sequenceName = "person_id_seq",allocationSize = 1)
    private int personId;

    @Size(min = 3,message = "Name should be at least 3 characters")
    private String name;

    @NotBlank(message = "Mobile number should not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of 10 digits")
    private String mobileNum;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 5,message = "Password should be at least 5 characters long")
    /*Here we are using passwordValidator annotation to check if it's a weak password or not*/
    @MyPasswordValidatorAnnotation
    private String pwd;

    @NotBlank(message = "Confirm Password should not be blank")
    @Size(min = 5,message = "Confirm Password should be at least 5 characters long")
    /*Transient means we don't want this column to be used in any of the JPA CRUD operation as there is
    * no need to add ConfirmPwd again in database.*/
    @Transient
    private String confirmPwd;

    @NotBlank(message = "Email Should not be blank")
    @Email(message = "Please provide correct emailId")
    private String email;

    @NotBlank(message = "Confirm Email Should not be blank")
    @Email(message = "Please provide correct Confirm emailId")
    @Transient
    private String confirmEmail;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST,targetEntity = Roles.class)
    @JoinColumn(name = "role_id",referencedColumnName = "roleId",nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name = "address_id",referencedColumnName = "addressId",nullable = true)
    private Address address;

    /*For ManyToOne fetch type is Lazy by default as Many Persons/Students can be
    registered into one class(Class 5, Class 6)*/
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "class_id",referencedColumnName = "classId",nullable = true)
    private SKSClass sksClass;

}
