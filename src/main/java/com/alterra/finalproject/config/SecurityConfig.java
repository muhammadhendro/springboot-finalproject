package com.alterra.finalproject.config;

import com.alterra.finalproject.config.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                .antMatchers("/v1/auth/**").permitAll()
                .antMatchers("/v1/customer/**").permitAll()
                .antMatchers( HttpMethod.POST, "/v1/book").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.DELETE, "/v1/book").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.PUT, "/v1/book").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.POST, "/v1/category").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.DELETE, "/v1/category").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.PUT, "/v1/category").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.POST, "/v1/author").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.DELETE, "/v1/author").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.PUT, "/v1/author").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.POST, "/v1/payment").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.DELETE, "/v1/payment").hasAuthority("ADMIN")
                .antMatchers( HttpMethod.PUT, "/v1/payment").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        // remove session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // filter jwt
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
