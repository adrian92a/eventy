package pl.net.rogala.eventy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
// events
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
//                .antMatchers("/").hasAnyAuthority("ROLE_ORGANIZER")
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?status=error")
                .loginProcessingUrl("/login-post")
                .defaultSuccessUrl("/home")
        ;

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT u.email, u.password, 1 FROM user u WHERE u.email = ? ")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name " +
                        "FROM user u " +
                        "JOIN user_role ur ON u.id = ur.user_id " +
                        "JOIN role r ON ur.roles_id = r.id " +
                        "WHERE u.email = ?")
                .passwordEncoder(passwordEncoder);
    }
}
