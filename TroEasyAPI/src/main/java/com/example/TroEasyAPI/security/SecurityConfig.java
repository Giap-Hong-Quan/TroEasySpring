package com.example.TroEasyAPI.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthEntryPoint customAuthEntryPoint;
    // Tạo sẵn Bean mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Tắt CSRF (REST API dùng Token nên bắt buộc phải tắt)
                .formLogin(form -> form.disable()) // TẮT TRANG LOGIN MẶC ĐỊNH Ở ĐÂY
                .httpBasic(basic -> basic.disable())// Tắt popup đăng nhập mặc định của trình duyệt
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Phân quyền đường dẫn
                .authorizeHttpRequests(auth -> auth
                        // Mở tự do cho Auth và cả Swagger để test
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .anyRequest().authenticated() // Mọi API khác phải có Token
                )
                // Bắt lỗi 401 trả về JSON đồng nhất theo chuẩn ApiResponse
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthEntryPoint))
                // Gắn Filter kiểm tra Token chạy trước
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
