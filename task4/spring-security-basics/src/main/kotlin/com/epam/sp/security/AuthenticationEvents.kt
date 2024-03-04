package com.epam.sp.security

import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents(
    private val accountLockingService: AccountLockingService
) {
    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent?) {
        val name: String? = success?.authentication?.name
        accountLockingService.handleSuccess(name)
    }

    @EventListener
    fun onFailure(failures: AbstractAuthenticationFailureEvent?) {
        val name: String? = failures?.authentication?.name
        accountLockingService.handleFailure(name)
    }
}