package com.epam.sp.converters

import com.epam.sp.dto.Subscription
import com.epam.sp.dto.SubscriptionRequestDto
import com.epam.sp.repositories.UserRepository
import com.epam.sp.services.exceptions.UserNotFoundException
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SubscriptionRequestDtoToSubscriptionConverter(
    private val userRepository: UserRepository
): Converter<SubscriptionRequestDto, Subscription> {

    override fun convert(source: SubscriptionRequestDto): Subscription {
        val userId = source.userId
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException("can't find user by id=$userId") }
        return Subscription(source.id ?: 0L, user, LocalDate.now())
    }
}