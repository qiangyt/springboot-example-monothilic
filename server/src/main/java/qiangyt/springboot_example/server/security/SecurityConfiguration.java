package qiangyt.springboot_example.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;


/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.security.SecurityConfiguration
 */
@Getter
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CachingUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var authMgr = authenticationManager();

        http.cors().and()
                .csrf().disable() // disable CSRF because it is unnecessary for JWT stateless approach
                .authorizeRequests().anyRequest().permitAll() // permit all and we will use annotation to do authorization
                .and()
                //.addFilter(new JwtAuthenticationFilter(authMgr))
                .addFilterBefore(new CORSFilter(), JwtAuthorizationFilter.class)
                .addFilter(new JwtAuthorizationFilter(authMgr, getUserDetailsService()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // we need the password to encrypt JWT
        auth.eraseCredentials(false);

        auth.userDetailsService(getUserDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
