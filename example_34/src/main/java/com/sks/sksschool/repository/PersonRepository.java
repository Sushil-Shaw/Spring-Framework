package com.sks.sksschool.repository;

import com.sks.sksschool.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/*Here JpaRepository extends ListCrudRepository, ListPagingAndSortingRepository so we can use both.*/
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Person findByEmail(String email);
}
