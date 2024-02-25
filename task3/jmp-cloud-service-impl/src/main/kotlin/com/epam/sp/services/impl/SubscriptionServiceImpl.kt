package com.epam.sp.services.impl

import com.epam.sp.dto.Subscription
import com.epam.sp.dto.SubscriptionRequestDto
import com.epam.sp.dto.SubscriptionResponseDto
import com.epam.sp.dto.annotations.NoArg
import com.epam.sp.repositories.SubscriptionRepository
import com.epam.sp.services.SubscriptionService
import com.epam.sp.services.exceptions.ConversionException
import com.epam.sp.services.exceptions.SubscriptionCreationException
import com.epam.sp.services.exceptions.SubscriptionUpdateException
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@NoArg
@Service
class SubscriptionServiceImpl(
    private val subscriptionRepository: SubscriptionRepository,
    private val conversionService: ConversionService
): SubscriptionService {
    override fun createSubscription(subscriptionRequest: SubscriptionRequestDto): SubscriptionResponseDto {
        val subscriptionToCreate = conversionService.convert(subscriptionRequest, Subscription::class.java) ?:
            throw ConversionException("Cannot convert request to Subscription object")
        val subscription = subscriptionRepository.save(subscriptionToCreate)
        return conversionService.convert(subscription, SubscriptionResponseDto::class.java) ?:
            throw SubscriptionCreationException("Cannot convert Subscription to the response")
    }

    override fun updateSubscription(subscriptionRequest: SubscriptionRequestDto): SubscriptionResponseDto {
        // find-update-save is not efficient, you'd better reuse the existing subscription object,
        // but I don't have time to investigate how it should work with Kotlin data classes
        val inputSubscription = conversionService.convert(subscriptionRequest, Subscription::class.java) ?:
            throw ConversionException("Cannot convert request to Subscription class")
        val subscriptionToUpdate = subscriptionRepository
            .findById(inputSubscription.id)
            .orElseThrow { SubscriptionUpdateException("Cannot find subscription by id") }
        val updatedSubscription = subscriptionToUpdate.copy(
            user = inputSubscription.user,
            startDate = inputSubscription.startDate
        )
        val subscription = subscriptionRepository.save(updatedSubscription)
        return conversionService.convert(subscription, SubscriptionResponseDto::class.java) ?:
        throw SubscriptionCreationException("Cannot convert Subscription to the response")
    }

    override fun deleteSubscription(id: Long): Long {
        subscriptionRepository.deleteById(id)
        return id
    }

    override fun getSubscription(id: Long): SubscriptionResponseDto? {
        return subscriptionRepository
            .findById(id)
            .map { conversionService.convert(it, SubscriptionResponseDto::class.java) ?:
                throw ConversionException("Cannot convert subscription to the response")}
            .orElse(null)
    }

    override fun getAllSubscriptions(): List<SubscriptionResponseDto> {
        return subscriptionRepository
            .findAll()
            .map { conversionService.convert(it, SubscriptionResponseDto::class.java) ?:
                throw ConversionException("Cannot convert user to the response")}
            .toList()
    }
}