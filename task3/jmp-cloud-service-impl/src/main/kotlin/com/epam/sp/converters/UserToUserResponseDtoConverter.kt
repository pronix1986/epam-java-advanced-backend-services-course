package com.epam.sp.converters

import com.epam.sp.dto.User
import com.epam.sp.dto.UserResponseDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class UserToUserResponseDtoConverter: Converter<User, UserResponseDto> {

    override fun convert(source: User): UserResponseDto {
        return UserResponseDto(
            source.id,
            source.name,
            source.surname,
            source.birthday.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}