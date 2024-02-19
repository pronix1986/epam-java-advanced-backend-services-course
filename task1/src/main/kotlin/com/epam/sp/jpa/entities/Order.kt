package com.epam.sp.jpa.entities

import jakarta.annotation.Generated
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Order (
    @Id
    @GeneratedValue
    val id: Long,
    val name: String
)