package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDao userDao;
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailService;

    public WebSecurityConfig(UserDao userDao, SuccessUserHandler successUserHandler, UserDetailsService userDetailService) {
        this.userDao = userDao;
        this.successUserHandler = successUserHandler;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll();
//                .antMatchers("/admin","/admin/*").hasRole("ADMIN")
//                .antMatchers("/registration", "/login","/error","/api/*").permitAll()
//                .and()
//                .formLogin().loginPage("/login")
//                .usernameParameter("email")
//                .loginProcessingUrl("/process_login")
//                .successHandler(successUserHandler)
//                .failureUrl("/login?error")
//                .and()
//                .logout()
//                .permitAll()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied");
    }

    // аутентификация inMemory
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User admin = new User("user","user", "user@mail.ru","33","123456");
        admin.addRole(new Role(Roles.adminRole()));
        userDao.saveUser(admin);
        auth.userDetailsService(userDetailService);
    }

    @Bean
    public PasswordEncoder getPasswordEncouder() {
        return NoOpPasswordEncoder.getInstance();
    }
}