package com.sks.sksschool.service;

import com.sks.sksschool.constants.SKSSchoolConstants;
import com.sks.sksschool.model.Person;
import com.sks.sksschool.model.Roles;
import com.sks.sksschool.repository.PersonRepository;
import com.sks.sksschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {

        boolean isCreated=false;

        Roles role=rolesRepository.getByRoleName(SKSSchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        //Hashing the Pwd using BCryptPasswordEncoder (See MyProjectSecurityConfig class) and save into db
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        Person savedPerson = personRepository.save(person);
        if(savedPerson.getPersonId()>0){
            isCreated=true;
        }
        return isCreated;
    }
}
