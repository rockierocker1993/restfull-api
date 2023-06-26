package id.rockierocker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    /**
     * Manage resource server.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     * Manage endpoints.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
        .and()
        .csrf().disable()
        .formLogin().permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/resources/**")
        .denyAll()
        .antMatchers("/api/v1/user/register").permitAll()
        .antMatchers("/api/v1/**")
        .authenticated()
        .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        ;
    }
}
