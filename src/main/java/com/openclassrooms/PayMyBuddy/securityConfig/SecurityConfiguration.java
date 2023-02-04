package com.openclassrooms.PayMyBuddy.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    // Authentification
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select email,password "
                    + "from users "
                    + "where email=?")
            .passwordEncoder(new BCryptPasswordEncoder());
        /*auth.inMemoryAuthentication()
            .withUser("user@mail.com")
            .password(passwordEncoder().encode("password"))
            .roles("USER");*/
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration to authorize requests
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/user/**")
                .hasRole("USER")
            .antMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                    .permitAll()
                    .loginProcessingUrl("/login_auth")
                    .defaultSuccessUrl("/homepage", true)
                    .failureUrl("/login?error=true")
            .and()
            .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
    }
}
