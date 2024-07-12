package uz.interlab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.interlab.security.AuthService;
import uz.interlab.security.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    private final AuthService authService;

    private final String[] WHITE_LIST = {"/auth/**", "/user/**", "/", "/sale/**", "/photo/**", "/navbar/**", "/footer/**",
            "/blog/**", "/banner/**", "/application/**", "/analysis-detail/**", "/analysis/**",
            "/achievement/**", "/about-us-page/**", "/vacancy/**", "/service/**", "/service-details/**",
            "/license/**", "/contact/**", "/legal/**", "/instruction/**", "/doctor/**", "/doctor-details/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationRequest ->
                        authorizationRequest.requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(authService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .rememberMe(remember -> remember.rememberMeCookieDomain("Authorization"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
