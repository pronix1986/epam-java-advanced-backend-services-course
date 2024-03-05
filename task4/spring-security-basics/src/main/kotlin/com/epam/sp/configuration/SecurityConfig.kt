package com.epam.sp.configuration

import com.epam.sp.security.CustomAuthenticationFailureHandler
import com.epam.sp.security.CustomUserDetailsService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/about", permitAll)
                authorize(HttpMethod.GET, "/login", permitAll)
                authorize(HttpMethod.GET, "/css/**", permitAll)
                authorize(HttpMethod.GET, "/info", hasRole("VIEW_INFO"))
                authorize(HttpMethod.GET, "/admin", hasRole("VIEW_ADMIN"))
                authorize(anyRequest, authenticated)
            }
            formLogin {
                loginPage = "/login"
                defaultSuccessUrl("/", false)
                permitAll()
                authenticationFailureHandler = CustomAuthenticationFailureHandler()
                failureUrl = "/login?error=true"
            }
            logout {
                logoutSuccessUrl = "/login?logout"
                permitAll()
            }
            httpBasic { }
        }
        return http.build()
    }


    // For task 4.3
    /*@Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user")
            .password("{bcrypt}\$2a\$12\$nrtKzcmzJIx7awQHeABKT.GNxeOBzCJhDyLANWdXoalKQayGO0zKa") // "password"
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }*/

    // For task 4.4
/*    @Bean
    fun users(dataSource: DataSource): UserDetailsManager = JdbcUserDetailsManager(dataSource)*/

    // For task 4.9
    @Bean
    @Primary
    fun users(dataSource: DataSource): UserDetailsManager = CustomUserDetailsService(dataSource)

    @Bean
    fun authenticationManager(userDetailsService: UserDetailsService,
                              passwordEncoder: PasswordEncoder,
                              applicationEventPublisher: ApplicationEventPublisher?): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(authenticationProvider).apply {
            this.setAuthenticationEventPublisher(DefaultAuthenticationEventPublisher(applicationEventPublisher))

        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()


/*    @Autowired
    fun configure(builder: AuthenticationManagerBuilder) {
        builder.eraseCredentials(false)
    }*/

/*
    @Bean
    fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher =
        DefaultAuthenticationEventPublisher(applicationEventPublisher)
*/


}
