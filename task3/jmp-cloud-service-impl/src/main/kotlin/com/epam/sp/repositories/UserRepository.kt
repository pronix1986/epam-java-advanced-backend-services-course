package com.epam.sp.repositories

import com.epam.sp.dto.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>