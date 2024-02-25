package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg

@NoArg
data class SubscriptionRequestDto(
    val id: Long?,
    val userId: Long
)
