package com.sks.sksschool.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Roles extends MyBaseEntity {

    @Id
   /* @GeneratedValue(strategy = GenerationType.AUTO,generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq",allocationSize = 1)*/
    private int roleId;

    private String roleName;

}
