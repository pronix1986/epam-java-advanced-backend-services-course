package com.epam.sp.controllers

import com.epam.sp.models.CachedValue
import com.epam.sp.security.AccountLockingService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/")
class AdminController(
    private val accountLockingService: AccountLockingService
) {

    @GetMapping("/admin")
    fun admin() = "admin"

    @GetMapping("/blocked-users")
    @ResponseBody
    fun blockedUsers(): Map<String, CachedValue>  {
        return accountLockingService.getFailedLogins()
    }

}
