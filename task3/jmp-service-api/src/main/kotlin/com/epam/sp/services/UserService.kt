package com.epam.sp.services

import com.epam.sp.dto.UserRequestDto
import com.epam.sp.dto.UserResponseDto
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun createUser(userRequest: UserRequestDto): UserResponseDto

    fun updateUser(userRequest: UserRequestDto): UserResponseDto

    fun deleteUser(id: Long): Long

    fun getUser(id: Long): UserResponseDto?

    fun getAllUsers(): List<UserResponseDto>

}