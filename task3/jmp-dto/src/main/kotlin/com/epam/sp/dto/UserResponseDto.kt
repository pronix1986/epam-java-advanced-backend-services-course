package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg
import org.springframework.hateoas.EntityModel

@NoArg
data class UserResponseDto(
    val id: Long,
    val name: String,
    val surname: String,
    val birthday: String
): EntityModel<UserResponseDto>()

