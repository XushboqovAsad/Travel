package uz.market.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uz.market.backend.security.JWTConfigurer;
import uz.market.backend.security.TokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final TokenProvider tokenProvider;

    public SecurityConfiguration(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("api/login").permitAll()
                .requestMatchers("api/register").permitAll()
                .requestMatchers("api/role").permitAll()
                .requestMatchers("api/account").permitAll()
                .requestMatchers("api/admin/assign-role").permitAll()
                .requestMatchers("api/delete/account/{id}").permitAll()
                .requestMatchers("api/product/add").hasRole("ADMIN")
                .requestMatchers("api/product/{id}").permitAll()
                .requestMatchers("api/product/edit").hasRole("ADMIN")
                .requestMatchers("api/xizmat/add").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(configurer());
        return http.build();
    }
    private JWTConfigurer configurer(){
        return new JWTConfigurer(tokenProvider);
    }
}
