package com.epam.sp.configuration

import com.epam.sp.converters.SubscriptionRequestDtoToSubscriptionConverter
import com.epam.sp.converters.SubscriptionToSubscriptionResponseDtoConverter
import com.epam.sp.converters.UserRequestDtoToUserConverter
import com.epam.sp.converters.UserToUserResponseDtoConverter
import com.epam.sp.dto.annotations.AllOpen
import com.epam.sp.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.support.DefaultConversionService

@AllOpen
@Configuration
class ConversionConfig {

    @Autowired @Lazy
    private lateinit var userRepository: UserRepository

    @Bean
    fun conversionService(): ConversionService =
        DefaultConversionService().also {
            it.addConverter(UserRequestDtoToUserConverter())
            it.addConverter(UserToUserResponseDtoConverter())
            it.addConverter(SubscriptionRequestDtoToSubscriptionConverter(userRepository))
            it.addConverter(SubscriptionToSubscriptionResponseDtoConverter())
        }


}

