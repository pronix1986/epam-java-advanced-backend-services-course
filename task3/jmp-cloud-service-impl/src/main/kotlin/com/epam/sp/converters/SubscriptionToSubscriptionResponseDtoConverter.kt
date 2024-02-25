package com.epam.sp.converters

import com.epam.sp.dto.Subscription
import com.epam.sp.dto.SubscriptionResponseDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class SubscriptionToSubscriptionResponseDtoConverter: Converter<Subscription, SubscriptionResponseDto> {

    override fun convert(source: Subscription): SubscriptionResponseDto {
        return SubscriptionResponseDto(
            source.id,
            source.user.id,
            source.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}