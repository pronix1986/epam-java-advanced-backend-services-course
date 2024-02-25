package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg

@NoArg
data class SubscriptionResponseDto(
    val id: Long,
    val userId: Long,
    val startDate: String
)
