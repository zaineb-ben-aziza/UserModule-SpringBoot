package com.example.khademni.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {
    private final JwtAuthenticatorFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
//    http
//            .cors(Customizer.withDefaults())
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(req ->
//                    req.requestMatchers(
//                            "",
//                            ""
//
//
//                    ).permitAll()
//                            .anyRequest()
//                            .authenticated()
//            )
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/")
//               .permitAll().anyRequest().authenticated().and().sessionManagement()
//               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//               .and
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        HttpSecurity httpSecurity = http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/api/v1/auth/").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
            .csrf(csrf -> csrf // prevention contre l'attack Cross-Site Request Forgery (CSRF)
                    .disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(requests -> requests // accepter les requetes http
                    .requestMatchers("/test","/users","/api/v1/auth/register","/api/v1/auth/authenticate","/users","/activateAccount","/api/v1/auth/activateAccount","/Khademni-main/Khademni/templates/activateAccount.html",("/**"))// pour definir le white list
                    .permitAll()
                    .anyRequest()
                    .authenticated())
            .sessionManagement(management -> management
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}
}
