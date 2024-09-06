package com.sks.sksschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.

We can use separate annotation as well. Like if we want only getter then @Getter
* */

/*Here we started using Spring data jpa. Using this we won't write any query but it'll map the Pojo
* class with the database and perform the CRUD operation. Hence @Entity is declared and as the table
* name is different hence gave table_name or not required, it'll auto map*/
@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends MyBaseEntity{

    /*@Id means Primary key and need to mark a column as primary key. @Column_name is not required here
    * as the Column name is same but just gave an example*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "contact_id_seq")
    @SequenceGenerator(name = "contact_id_seq",sequenceName = "contact_id_seq",allocationSize = 1)
    @Column(name = "contact_id")
    private int contactId;

    //@NotBlank(message = "Name should not be blank")
    @Size(min = 3, message="Name should at least be 3 characters")
    private String name;

    @NotBlank(message = "Mobile number should not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of 10 digits")
    private String mobileNum;

    @NotBlank(message = "Email should not be blank")
    @Email
    private String email;

    //@NotBlank(message = "Subject should not be blank")
    @Size(min = 3, message="Subject should at least be 3 characters")
    private String subject;

   // @NotBlank(message = "Message should not be blank")
    @Size(min = 10, message="Message should at least be 10 characters")
    private String message;

    private String status;


    /*
    Commented as we started using Lombok library and we have used @Data above
    which will generate all these.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }*/
}
