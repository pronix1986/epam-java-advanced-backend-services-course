package com.epam.sp.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class CustomAuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {


    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        setDefaultFailureUrl("/login?error=true")
        request.session.setAttribute("LAST_USERNAME_KEY", request.getParameter("username"))
        super.onAuthenticationFailure(request, response, exception)
    }
}