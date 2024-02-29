package com.epam.sp.dto

import com.epam.sp.dto.annotations.NoArg
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.Past
import java.time.LocalDate

@Entity
@NoArg
data class Subscription (
    @Id @GeneratedValue
    val id: Long,
    @ManyToOne
    val user: User,
    @Past
    val startDate: LocalDate,
)