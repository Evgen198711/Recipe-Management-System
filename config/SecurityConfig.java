package recipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();

        http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable).headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/recipe/**").authenticated()
                        .requestMatchers("/api/register", "/error/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                        .anyRequest().denyAll());




        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
