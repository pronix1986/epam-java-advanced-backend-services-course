package com.epam.sp.jpa.repositories

import com.epam.sp.jpa.entities.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>