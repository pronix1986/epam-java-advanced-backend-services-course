package com.epam.sp.controllers

import com.epam.sp.security.AccountLockingService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginController(
    private val accountLockingService: AccountLockingService
) {
    @GetMapping("/login")
    fun login(@RequestParam(value = "error", defaultValue = "false") loginError: Boolean,
            model: Model, httpSession: HttpSession): String {

        getUserName(httpSession).let {
            if (loginError && accountLockingService.userIsLocked(it)) {
                model.addAttribute("accountLocked", true)
            }
        }
        return "login"
    }

    fun getUserName(session: HttpSession): String? {
        val username = session.getAttribute("LAST_USERNAME_KEY") as? String
        if (!username.isNullOrBlank()) {
            session.removeAttribute("LAST_USERNAME_KEY")
        }
        return username
    }

}
