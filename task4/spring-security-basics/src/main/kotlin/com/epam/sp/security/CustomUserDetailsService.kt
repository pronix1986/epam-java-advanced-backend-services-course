package com.epam.sp.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class CustomUserDetailsService(
    dataSource: DataSource
    ): JdbcUserDetailsManager(dataSource) {

    @Autowired
    private lateinit var accountLockingService: AccountLockingService

    @Override
    override fun loadUsersByUsername(login: String?): List<UserDetails>? {
        if (accountLockingService.userIsLocked(login)) {
            throw LockedException("User $login is locked due to the number of authentication attempt")
        }
        return super.loadUsersByUsername(login)
    }

}