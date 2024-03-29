package com.hanaberia.config;

import com.hanaberia.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/webjars/**", "/users/to-add","/users/add", "/styles/**", "/js/**").permitAll()
                .antMatchers("/archive", "/messages/add", "/messages/to-add", "/recover/**").permitAll()
                .antMatchers("/products/**", "/orders/all", "/messages/all").hasAuthority("ADMIN")
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error").permitAll().and()
                .logout().logoutSuccessUrl("/index.html").permitAll()
                .logoutSuccessUrl("/").and()
                .exceptionHandling().accessDeniedPage("/403");

        http
                .csrf().disable();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
