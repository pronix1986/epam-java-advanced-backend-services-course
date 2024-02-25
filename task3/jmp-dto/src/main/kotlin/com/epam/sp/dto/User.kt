package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Entity
@NoArg
data class User(
    @Id @GeneratedValue
    val id: Long,
    @Size(min = 1)
    val name: String,
    @Size(min = 1)
    val surname: String,
    @Past
    val birthday: LocalDate,
)
