package com.epam.sp.controllers

import com.epam.sp.dto.SubscriptionRequestDto
import com.epam.sp.dto.SubscriptionResponseDto
import com.epam.sp.services.SubscriptionService
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subscriptions")
class ServiceController(
            private val subscriptionService: SubscriptionService
    ) {

    @PostMapping("/")
    fun createSubscription(@Validated @RequestBody subscriptionRequestDto: SubscriptionRequestDto): ResponseEntity<SubscriptionResponseDto> {
        val subscriptionResponse = subscriptionService.createSubscription(subscriptionRequestDto)
        val link = WebMvcLinkBuilder.linkTo(this::class.java).slash(subscriptionResponse.id).toUri()
        return ResponseEntity.created(link).body(subscriptionResponse)
    }

    @PutMapping("/")
    fun updateSubscription(@Validated @RequestBody subscriptionRequestDto: SubscriptionRequestDto): ResponseEntity<SubscriptionResponseDto> {
        val subscriptionResponse = subscriptionService.updateSubscription(subscriptionRequestDto)
        // PUT can return 200/204 depending on the response. In our case, we return the updated object
        return ResponseEntity.ok(subscriptionResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteSubscription(@PathVariable id: Long): ResponseEntity<Long> {
        val deletedId = subscriptionService.deleteSubscription(id)
        val link = WebMvcLinkBuilder.linkTo(this::class.java).slash(deletedId).toUri()
        return ResponseEntity.noContent().location(link).build()
    }

    @GetMapping("/{id}")
    fun getSubscription(@PathVariable id: Long): ResponseEntity<SubscriptionResponseDto> {
        val subscriptionResponse = subscriptionService.getSubscription(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(subscriptionResponse)
    }

    @GetMapping("/")
    fun getAllSubscriptions(): ResponseEntity<List<SubscriptionResponseDto>> {
        val subscriptionListResponse = subscriptionService.getAllSubscriptions()
        return ResponseEntity.ok(subscriptionListResponse)
    }
}
