package com.epam.sp.repositories

import com.epam.sp.dto.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository: JpaRepository<Subscription, Long>