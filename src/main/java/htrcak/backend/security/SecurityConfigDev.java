package htrcak.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

// TODO these annotations are
@Profile("dev")
@Configuration
@Component("disableSecurityConfigurationBean")
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfigDev extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**");
    }

    // auth
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                    .anyRequest()
                        .authenticated();

    }*/
}
