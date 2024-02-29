package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg

@NoArg
data class UserRequestDto (
    val id: Long?,
    val name: String,
    val surname: String,
    val birthday: String,
)