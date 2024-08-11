package com.sks.sksschool.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address extends MyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq",allocationSize = 1)
    private int addressId;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private Long zipCode;
}
