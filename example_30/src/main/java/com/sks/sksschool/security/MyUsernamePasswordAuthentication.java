package com.sks.sksschool.security;

import com.sks.sksschool.model.Person;
import com.sks.sksschool.model.Roles;
import com.sks.sksschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUsernamePasswordAuthentication implements AuthenticationProvider {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();
        Person person=personRepository.findByEmail(email);

        if(null !=person && person.getPersonId()>0 && pwd.equals(person.getPwd())){
            return new UsernamePasswordAuthenticationToken(person.getName(),pwd,
                    getGrantedAuthority(person.getRoles()));
        }else{
            throw new BadCredentialsException("Invalid Credentials");
        }

    }

    private List<GrantedAuthority> getGrantedAuthority(Roles roles) {
        List<GrantedAuthority> list= List.of
                (new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return list;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
