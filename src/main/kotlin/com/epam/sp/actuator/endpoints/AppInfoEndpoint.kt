package com.epam.sp.actuator.endpoints

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


@Component
@Endpoint(id="appinfo")
class AppInfoEndpoint(
    @Autowired private val env: Environment
    ) {

    @ReadOperation
    fun getAppInfo(): Map<String, String> =
        mapOf(
            "active-profiles" to env.activeProfiles.contentDeepToString(),
            "current-db-url" to env.getProperty("app.datasource.url", "UNKNOWN")
        )


}