package group.finp_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(MyUserDetailsService myUserDetailsService,
                          UnauthorizedEntryPoint unauthorizedEntryPoint,
                          AccessDeniedHandler accessDeniedHandler) {
        this.myUserDetailsService = myUserDetailsService;
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable()) // CSRF protection disable
                .headers(headers -> headers.frameOptions().disable()) // Disable frame options
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // Allow H2 Console access
                        .requestMatchers("/", "/login/**").permitAll() // Allow public access to home and login
                        .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole("USER") // Secure post endpoints for USER role
                        .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole("ADMIN") // Secure admin endpoints for ADMIN role
                        .anyRequest().authenticated() // All other requests must be authenticated
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .formLogin(form -> form
                        .loginPage("/login/login") // Custom login page
                        .usernameParameter("username") // Username parameter
                        .passwordParameter("password") // Password parameter
                        .loginProcessingUrl("/login/login-proc") // Login processing URL
                        .defaultSuccessUrl("/", true) // Redirect after successful login
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Custom logout URL
                        .logoutSuccessUrl("/") // Redirect after logout
                )
                .userDetailsService(myUserDetailsService); // Set custom user details service

        return http.build();
    }
}