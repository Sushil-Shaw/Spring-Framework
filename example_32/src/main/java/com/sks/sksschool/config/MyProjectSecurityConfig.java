package com.sks.sksschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.PathVariable;

@Configuration
public class MyProjectSecurityConfig {

    @Bean
    SecurityFilterChain applySecurity(HttpSecurity http) throws Exception {

        /* Cross-Site Request Forgery (CSRF) is an attack where hackers steal the access token from cookies
        * and act like you and send the request. By default, Spring blocks all Post/Put requests and it'll give
        * 403 error. But if we use thymeleaf in that html form means instead of <form action="/home", if we
        * use thymeleaf <form th:action="@{home}" then Spring allows the post/put request. As thymeleaf
        * internally handles CSRF. But if we want, we can disable this and Spring will allow this Post/Put
        * request without thymeleaf.Here we have disabled below.*/

        /*        Permit all the requests without Authentication. So no login page*//*
        http.authorizeHttpRequests(requests ->{
            requests.anyRequest().permitAll();
        });

         /*Deny all the requests without Authentication. So no login page*//*
        http.authorizeHttpRequests(requests ->{
            requests.anyRequest().denyAll();
        });*/

        /*permitAll() means giving permit to that page without Security
         * authenticated() means needs to be authenticated using username and password
         * denyAll() means you will get first login page, but after successful login also,get 403 forbidden error
         * */

        http.csrf( csrf -> csrf.ignoringRequestMatchers("/saveMsg")
                        .ignoringRequestMatchers("/public/**"))
                        //.ignoringRequestMatchers(PathRequest.toH2Console()))
                .authorizeHttpRequests(requests ->
                        /*here authenticated() means need to login first for this page*/
                        requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/displayProfile").authenticated()
                        .requestMatchers("/updateProfile").authenticated()
                        .requestMatchers("/displayMessages").hasRole("ADMIN")
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/error").permitAll()
/*Our holiday path is like /holiday/all,holiday/national so
here /holiday*//** means all path variable after holiday*/
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                        /*Giving permission to H2 database*/
                       // .requestMatchers(PathRequest.toH2Console()).permitAll()
/*generally by default Spring secures all things, So here css.js all are secured under /assets folder
* So if you don't give permit here, the page won't load properly*/
                        .requestMatchers("/assets/**").permitAll());

                http.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll());

               /*After csrf protection is enabled, Spring blocks the logout url and hence need to
               * manually create mapping of logout url such as /logout. So below one is not useful
               * after this*/
               /*http.logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll());*/

        /*By default Spring Security blocks everything so here we are disabling the header frame. So
        * the /h2-console database will be shown properly*/
        //http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

               http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

/*    Here we are using PasswordEncoder to store the Hashed password and at the time of login, compare
* the entered password with the stored Hashed password in the db. Implementation we are using here is
* BCryptPasswordEncoder (most commonly used)*/
    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
    /*User.withDefaultPasswordEncoder() is deprecated. But we need to provide the password encoder.
    * if we don't want to encode then we need to pass {noop}, otherwise it won't work.*/

    /* as we have defined password fetching from db, commenting inMemory one.
    see MyUsernamePasswordAuthentication class */

    /*@Bean
    public InMemoryUserDetailsManager userDetailsService(){

        UserDetails user=User.builder().username("sks").password("{noop}sks").roles("USER").build();
        UserDetails admin =
                User.builder().username("admin").password("{noop}admin").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user,admin);
    }*/

    /*@Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("12345")
                .roles("USER").build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("54321")
                .roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

}
