package codehouse.simparty.config;

import codehouse.simparty.security.handler.MemberLoginSuccessHandler;
import codehouse.simparty.security.service.MemberUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberUserDetailsService memberUserDetailsService; // 주입

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/member").hasRole("USER");
        http.formLogin(); // 인가/인증에 문제시 로그인 화면
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService()); // 7days
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 사용자 계정은 user1
        auth.inMemoryAuthentication().withUser("user1")
                // 1111 패스워드 인코딩 결과
                .password("$2a$10$z9Vj9/GlmykWnjyBRoinCeHxFGXaEj8sTXV1FRgD5fYklwzFqFEuS")
                .roles("USER");
    }*/
}
