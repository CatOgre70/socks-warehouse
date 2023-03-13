package pro.sky.sockswarehouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
//        "/swagger-ui/index.html",
        "/swagger-ui.html",
        "/v3/api-docs",
        "/webjars/**",
        // -- Login and register end points
        "/login",
        "/register"
    };

    @Bean
    protected UserDetailsManager userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .mvcMatchers(AUTH_WHITELIST).permitAll()
                        .mvcMatchers("/api/**", "/users/**").authenticated()
                )
                .cors().and()
                .httpBasic(withDefaults())
                .logout();
        return http.build();
    }

}
