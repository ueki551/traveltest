package com.example.travelplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.travelplan.service.MyUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    public SecurityConfig(MyUserDetailsService uds) {
        this.userDetailsService = uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Spring Security に独自の UserDetailsService を登録
                .userDetailsService(userDetailsService)

                // (1) 開発中の簡易化のため CSRF を一旦無効化
                .csrf(csrf -> csrf.disable())

                // (2) URL ごとのアクセス制御
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**",
                                "/h2-console/**"
                        ).permitAll()
                        // それ以外はすべて認証必須
                        .anyRequest().authenticated()
                )

                // (3) H2 コンソールは <frame> を使って表示するため、同一オリジンからのフレームを許可
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // (4) フォームログインの設定
                .formLogin(form -> form
                        .loginPage("/login")             // GET /login でログインフォームを表示
                        .defaultSuccessUrl("/search", true)  // 認証後に /search へ
                        .permitAll()
                )

                // (5) ログアウト
                .logout(Customizer.withDefaults());

        return http.build();
    }
}
