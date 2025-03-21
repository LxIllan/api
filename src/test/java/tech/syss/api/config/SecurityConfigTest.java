package tech.syss.api.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class SecurityConfigTest {
    @Bean
    @Primary
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        return authenticationManager;
    }

    @Bean
    @Primary
    public AuthenticationConfiguration authenticationConfiguration() {
        return mock(AuthenticationConfiguration.class);
    }

    @Bean
    public HttpSecurity httpSecurity () throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);
        when(http.csrf(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
        return http;
    }

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return mock(SecurityFilterChain.class);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return mock(BCryptPasswordEncoder.class);
    }

    @Bean
    public ObjectPostProcessor<Object> objectPostProcessor() {
        return new ObjectPostProcessor<>() {
            @Override
            public <T> T postProcess(T object) {
                return object;
            }
        };
    }
}
