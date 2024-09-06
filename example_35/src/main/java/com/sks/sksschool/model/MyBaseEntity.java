package com.sks.sksschool.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
/*As it is a Super class and require every place when any class extends this class. Hence this @*/
@MappedSuperclass

/*This entity listeners we are using here for Auditing purpose*/
@EntityListeners(AuditingEntityListener.class)
public class MyBaseEntity {

    /*Now we won't pass these fields during Contact save because it'll be automatically done by JPA
    * updated=false means when we'll update any record then don't add these fields*/
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
