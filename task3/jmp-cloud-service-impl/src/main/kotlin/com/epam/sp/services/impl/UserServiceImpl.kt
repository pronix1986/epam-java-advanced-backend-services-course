package com.epam.sp.services.impl

import com.epam.sp.dto.User
import com.epam.sp.dto.UserRequestDto
import com.epam.sp.dto.UserResponseDto
import com.epam.sp.dto.annotations.NoArg
import com.epam.sp.repositories.UserRepository
import com.epam.sp.services.UserService
import com.epam.sp.services.exceptions.ConversionException
import com.epam.sp.services.exceptions.UserCreationException
import com.epam.sp.services.exceptions.UserUpdateException
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@NoArg
@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val conversionService: ConversionService
): UserService {

    override fun createUser(userRequest: UserRequestDto): UserResponseDto {
        val userToCreate = conversionService.convert(userRequest, User::class.java) ?:
            throw ConversionException("Cannot convert request to User object")
        val user = userRepository.save(userToCreate)
        return conversionService.convert(user, UserResponseDto::class.java) ?:
            throw UserCreationException("Cannot convert User to the response")
    }

    override fun updateUser(userRequest: UserRequestDto): UserResponseDto {
        // find-update-save is not efficient, you'd better reuse the existing user object,
        // but I don't have time to investigate how it should work with Kotlin data classes
        val inputUser = conversionService.convert(userRequest, User::class.java) ?:
            throw ConversionException("Cannot convert request to User class")
        val userToUpdate = userRepository.findById(inputUser.id).orElseThrow { UserUpdateException("Cannot find user by id") }
        val updatedUser = userToUpdate.copy(
            name = inputUser.name,
            surname = inputUser.surname,
            birthday = inputUser.birthday
        )
        val user = userRepository.save(updatedUser)
        return conversionService.convert(user, UserResponseDto::class.java) ?:
            throw UserCreationException("Cannot convert User to the response")
    }

    override fun deleteUser(id: Long): Long {
        userRepository.deleteById(id)
        return id
    }

    override fun getUser(id: Long): UserResponseDto? {
        return userRepository
            .findById(id)
            .map { conversionService.convert(it, UserResponseDto::class.java) ?:
                throw ConversionException("Cannot convert user to the response")}
            .orElse(null)
    }

    override fun getAllUsers(): List<UserResponseDto> {
        return userRepository
            .findAll()
            .map { conversionService.convert(it, UserResponseDto::class.java) ?:
                throw ConversionException("Cannot convert user to the response")}
            .toList()
    }
}