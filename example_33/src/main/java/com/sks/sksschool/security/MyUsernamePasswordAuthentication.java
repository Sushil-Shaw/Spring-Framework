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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/*We have created this class to compare the password with the DB. Earlier we were using
* InMemoryAuthentication where we had defined two credentials. But here we are using from DB.
* Need to implement AuthenticationProvider interface*/
@Component
public class MyUsernamePasswordAuthentication implements AuthenticationProvider {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //fetch Person Object with email. So need to pass email at the time of login
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();

        //this method we have defined in the PersonRepository class and JPA will prepare the query
        //by seeing findBy/readBy/getBy (all are same)
        Person person=personRepository.findByEmail(email);

        /*Here we are using PasswordEncoder to match the entered password with the Hashed password
        * stored in the DB. it'll generate the Hash of the entered password and then compare with the
        * stored hashed password. Note if the passwords are same also, it doesn't mean the Hash value
        * will be same only. It may be different but internally Java does hashing logic and find both
        * are same or not.*/
        if(null !=person && person.getPersonId()>0 && passwordEncoder.matches(pwd,person.getPwd())){
            /*Here returning UsernamePasswordAuthenticationToken class which implements Authentication
            * class hence no error. In the place of pwd, passing null. If we pass pwd also, Spring will
            * remove this as part of the Security.*/
            return new UsernamePasswordAuthenticationToken(email,null,
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
        /*This checks which Authentication implementation need to call. here we are calling
        * UsernamePasswordAuthenticationToken one, hence above authentication method will be invoked*/
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
