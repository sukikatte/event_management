package ucd.comp3013j.ems.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ucd.comp3013j.ems.model.services.EventService;
import ucd.comp3013j.ems.model.services.LoginService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/").permitAll()
                .requestMatchers("/register*").permitAll()
                .requestMatchers("/login*").permitAll()
                .requestMatchers("/createAccount*").permitAll()
                .requestMatchers("/administrator").hasAuthority("ADMINISTRATOR")
                .requestMatchers("/user").hasAuthority("USER")
                .requestMatchers("/organiser").hasAuthority("ORGANISER")
                .requestMatchers("/profile").permitAll()
                .requestMatchers("/updateProfile").permitAll()
                 .requestMatchers("/adminProfile").permitAll()
                 .requestMatchers("/updateAdminProfile").permitAll()
                .requestMatchers("/tickets").hasAuthority("USER")
                .requestMatchers("/buy_tickets").permitAll()
                .requestMatchers("/check_tickets").permitAll()
                .requestMatchers("/tickets/detail").hasAuthority("USER")
                                .requestMatchers("/displayEventDetailUser").hasAuthority("USER")
                .requestMatchers("/organiserProfile").permitAll()
                .requestMatchers("/updateOrganiserProfile").permitAll()
                .requestMatchers("/getAllEvents").permitAll()
                .requestMatchers("/addEvent").hasAuthority("ORGANISER")

                )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticate") // 设置表单提交的 URL
                .successHandler(new AuthSuccess()) // 使用 AuthSuccess 作为认证成功处理器
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginService();
    }

}
