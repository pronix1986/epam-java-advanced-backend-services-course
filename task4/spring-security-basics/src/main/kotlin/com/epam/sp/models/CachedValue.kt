package com.epam.sp.models

import java.time.LocalDateTime

class CachedValue {
    var attempts: Int = 0
    var blockedTimestamp: LocalDateTime = LocalDateTime.now()
    var blockedUntilTimestamp: LocalDateTime = LocalDateTime.now()
    fun registerAttempt() {
        attempts++
    }
}
