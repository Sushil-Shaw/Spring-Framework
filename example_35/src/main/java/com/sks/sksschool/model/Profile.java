package com.sks.sksschool.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {

    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @Email(message = "Please provide valid email address")
    private String email;

    @Size(min = 10, message = "Address Line 1 should be at least 10 characters")
    private String address1;

    private String address2;

    @Size(min = 3, message = "City should at least be 3 chars")
    private String city;

    @Size(min = 2, message = "State should at least be 2 chars")
    private String state;

    @Size(min = 3, message = "ZipCode should at least be 3 chars")
    private String zipCode;
}
