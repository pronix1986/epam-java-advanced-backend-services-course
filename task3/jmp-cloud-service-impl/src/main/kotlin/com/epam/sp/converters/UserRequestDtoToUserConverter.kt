package com.epam.sp.converters

import com.epam.sp.dto.User
import com.epam.sp.dto.UserRequestDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class UserRequestDtoToUserConverter: Converter<UserRequestDto, User> {
    override fun convert(source: UserRequestDto): User {
        return User(source.id?:0L, source.name, source.surname,
            LocalDate.parse(source.birthday, DateTimeFormatter.ISO_LOCAL_DATE))
    }
}