package com.epam.sp.services.exceptions

open class JmpException(message: String): RuntimeException(message)

open class ConversionException(message: String): RuntimeException(message)

open class UserException(message: String): JmpException(message)
class UserCreationException(message: String): UserException(message)
class UserUpdateException(message: String): UserException(message)
class UserNotFoundException(message: String): UserException(message)

open class SubscriptionException(message: String): JmpException(message)
class SubscriptionCreationException(message: String): SubscriptionException(message)
class SubscriptionUpdateException(message: String): SubscriptionException(message)
class SubscriptionNotFoundException(message: String): SubscriptionException(message)
