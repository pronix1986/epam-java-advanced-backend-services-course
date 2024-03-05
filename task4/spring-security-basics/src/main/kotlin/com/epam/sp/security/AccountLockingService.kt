package com.epam.sp.security

import com.epam.sp.models.CachedValue
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

const val BLOCK_DURATION_SEC = 60L
const val LOCK_THRESHOLD = 3
@Service
class AccountLockingService {

    private val cache: LoadingCache<String, CachedValue> = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofSeconds(BLOCK_DURATION_SEC))
        .build(object: CacheLoader<String, CachedValue>() {
            override fun load(key: String): CachedValue {
                return CachedValue();
            }
        })

    fun handleFailure(login: String?) {
        val cachedValue: CachedValue = cache[login]
        cachedValue.registerAttempt()
        if (userIsLocked(login)) {
            cachedValue.blockedTimestamp = LocalDateTime.now()
            cachedValue.blockedUntilTimestamp = cachedValue.blockedTimestamp.plusSeconds(BLOCK_DURATION_SEC)
        }

        cache.put(login, cachedValue)
    }

    fun handleSuccess(login: String?) {
        cache.invalidate(login)
    }

    fun getFailedLogins(): Map<String, CachedValue> = cache.asMap()

    fun userIsLocked(login: String?): Boolean {
        return cache[login].attempts >= LOCK_THRESHOLD
    }
}