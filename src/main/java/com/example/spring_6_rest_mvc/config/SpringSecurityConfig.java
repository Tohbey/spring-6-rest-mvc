package com.example.spring_6_rest_mvc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// disabling spring security config for the test profile.
@Profile("!test")
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        Http basic
//        http.authorizeHttpRequests
//                (auth -> auth.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

//      Oauth 2 Setup
        http.authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/v3/api-docs**", "/swagger-ui/**",  "/swagger-ui.html").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> {
                    httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults());
                });

        return http.build();
    }
}
