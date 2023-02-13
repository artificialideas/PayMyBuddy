package com.openclassrooms.PayMyBuddy.securityConfig;

import com.openclassrooms.PayMyBuddy.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailService userDetailService;

    //Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentification
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication()
                .withUser("naples1985@example.org").password(passwordEncoder().encode("naples1985")).roles("USER");
    }

    // Configuration to authorize requests
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/signin**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                        .defaultSuccessUrl("/user/index", true)
                        .failureUrl("/login?error=true")
                .and()
                .logout()
                    .invalidateHttpSession(true).clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll();
    }
}
