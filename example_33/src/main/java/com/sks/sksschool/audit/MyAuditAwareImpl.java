package com.sks.sksschool.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*Using this AuditorAware interface we are getting the current user which we are using in
* createBy/updatedBy column in MyBaseEntity class*/
@Component("myAuditAwareImpl")
public class MyAuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
