/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group9.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception
  {
    auth
      .inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
  }
	
protected void configure(HttpSecurity http) throws Exception {
            
    
    http.authorizeRequests()
          .antMatchers("/login", "/").permitAll()
            .anyRequest().authenticated()
            .and()
            
            
            
        .formLogin()
          .loginPage("/login")
            .and()
        .httpBasic();

  }
}