package org.shunin.firstsecurityapp.config;


import org.shunin.firstsecurityapp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // add lesson 79
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // конфигурируем сам Spring Security
        // конфигурируем авторизацию
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()  // всех пускаем на эти две страницы
                .anyRequest().authenticated()  // все другие должны авторизироваться. Эта и две верхние строки - авторизация
                .and()// а здесь уже идет аутентификация
                .formLogin().loginPage("/auth/login")  // вначале направляет на эту страницу
                .loginProcessingUrl("/process_login")   // ловит ответ от нее и пытается аутентифицироваться
                .defaultSuccessUrl("/hello", true) // после успешней аутент. перенапр-ет на /hello
                .failureUrl("/auth/login?error")  // после неуспешной аутент. перенапр-ет на /auth/login?error
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }


    // Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
