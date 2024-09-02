package com.sks.sksschool.repository;

import com.sks.sksschool.model.SKSClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKSClassRepository extends JpaRepository<SKSClass,Integer> {


}
