package com.sks.sksschool.repository;

import com.sks.sksschool.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {

    /*This is called Derived method. Here we can write the method signature only and JPA will create
    * query by its own. we need to put find/get/read/count By then column name means here
    * select * from roles where role_name=?*/

    Roles getByRoleName(String roleName);
}
