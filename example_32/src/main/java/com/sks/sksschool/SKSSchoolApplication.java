package com.sks.sksschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//If we want to exclude any class to be auto configured
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories("com.sks.sksschool.repository")
@EntityScan("com.sks.sksschool.model")
@EnableJpaAuditing(auditorAwareRef = "myAuditAwareImpl")
public class SKSSchoolApplication {

	public static void main(String[] args) {

		SpringApplication.run(SKSSchoolApplication.class, args);

	}

}
