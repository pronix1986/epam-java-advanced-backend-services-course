package com.epam.sp.controllers

import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/")
class InfoController {

    @Value("\${info-statistics}")
    private lateinit var statsKeys: List<String>

    @GetMapping("/info")
    fun getStats(restTemplate: RestTemplate): ResponseEntity<Stats> {
        val baseUriStr = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        val metricsUriStr = "$baseUriStr/actuator/metrics"
        val stats = Stats()

        statsKeys.forEach { key ->
            val metricUriStr = "$metricsUriStr/$key"
            val body = restTemplate.getForEntity<String>(metricUriStr).body
            val value: Number? = JsonPath.parse(body).read<Number>("$.measurements[0].value")
            stats.items[key] = value?.toString()
        }
        return ResponseEntity.ok(stats)
    }
}

data class Stats(
    val items: MutableMap<String, String?> = mutableMapOf()
)