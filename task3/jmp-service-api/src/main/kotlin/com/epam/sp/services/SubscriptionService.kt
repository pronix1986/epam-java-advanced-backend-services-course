package com.epam.sp.services

import com.epam.sp.dto.SubscriptionRequestDto
import com.epam.sp.dto.SubscriptionResponseDto
import org.springframework.stereotype.Service

@Service
interface SubscriptionService {
    fun createSubscription(subscriptionRequest: SubscriptionRequestDto): SubscriptionResponseDto

    fun updateSubscription(subscriptionRequest: SubscriptionRequestDto): SubscriptionResponseDto

    fun deleteSubscription(id: Long): Long

    fun getSubscription(id: Long): SubscriptionResponseDto?

    fun getAllSubscriptions(): List<SubscriptionResponseDto>

}