package com.upiiz.securitydb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.upiiz.securitydb.services.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/v1/facturas/**"))
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(http -> {
                http.requestMatchers(HttpMethod.GET, "/api/v1/facturas/listar/**").hasAnyAuthority("READ")
                        .requestMatchers(HttpMethod.POST, "/api/v1/facturas/crear/**").hasAnyAuthority("CREATE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/facturas/actualizar/**").hasAnyAuthority("UPDATE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/facturas/eliminar/**").hasAnyAuthority("DELETE")
                        //.requestMatchers(HttpMethod.PATCH, "/api/v1/facturas/deploy/**").hasAuthority("DEPLOY")
                        .anyRequest().denyAll();
            })
            .build();
}

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity
    //     .csrf(csrf -> csrf
    //     .ignoringRequestMatchers("/api/v1/facturas/**")) // Deshabilitar CSRF para facilitar pruebas (habilítalo en producción)
    //     .httpBasic(Customizer.withDefaults()) // Autenticación básica
    //     .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para APIs REST
    //     .authorizeHttpRequests(auth -> {
    //         // Configuración de permisos por endpoint
    //         auth.requestMatchers(HttpMethod.GET, "/api/v1/facturas/**").hasAuthority("READ");
    //         auth.requestMatchers(HttpMethod.POST, "/api/v1/facturas/**").hasAuthority("CREATE");
    //         auth.requestMatchers(HttpMethod.PUT, "/api/v1/facturas/**").hasAuthority("UPDATE");
    //         auth.requestMatchers(HttpMethod.DELETE, "/api/v1/facturas/**").hasAuthority("DELETE");
    //         // Permitir acceso al resto de los endpoints solo a usuarios autenticados
    //         auth.anyRequest().denyAll();
    //     })
    //     .build();
    // }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}


// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//     return httpSecurity
//             .csrf(csrf -> csrf
//                     .ignoringRequestMatchers("/api/v1/facturas/**"))
//             .httpBasic(Customizer.withDefaults())
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(http -> {
//                 http.requestMatchers(HttpMethod.GET, "/api/v1/facturas/listar/**").hasAnyAuthority("READ")
//                         .requestMatchers(HttpMethod.POST, "/api/v1/facturas/crear/**").hasAnyAuthority("CREATE")
//                         .requestMatchers(HttpMethod.PUT, "/api/v1/facturas/actualizar/**").hasAnyAuthority("UPDATE")
//                         .requestMatchers(HttpMethod.DELETE, "/api/v1/facturas/eliminar/**").hasAnyAuthority("DELETE")
//                         .requestMatchers(HttpMethod.PATCH, "/api/v1/facturas/deploy/**").hasAuthority("DEPLOY")
//                         .anyRequest().denyAll();
//             })
//             .build();
// }
