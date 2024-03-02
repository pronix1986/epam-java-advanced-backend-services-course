package com.epam.sp.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/about", hasRole("VIEW_INFO"))
                authorize(HttpMethod.GET, "/admin", hasRole("VIEW_ADMIN"))
                authorize(anyRequest, authenticated)
            }
            formLogin { }
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
    @Bean
    fun users(dataSource: DataSource): UserDetailsManager = JdbcUserDetailsManager(dataSource)

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()


    @Autowired
    fun configure(builder: AuthenticationManagerBuilder) {
        builder.eraseCredentials(false)
    }

}
