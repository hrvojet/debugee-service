package htrcak.backend.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO these annotations are
@Profile("dev")
@EnableWebSecurity
public class SecurityConfigDev extends WebSecurityConfigurerAdapter {

    // auth
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                    .anyRequest()
                        .permitAll();

    }
}
