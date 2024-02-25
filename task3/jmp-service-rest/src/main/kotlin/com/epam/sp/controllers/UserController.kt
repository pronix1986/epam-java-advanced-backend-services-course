package com.epam.sp.controllers

import com.epam.sp.dto.UserRequestDto
import com.epam.sp.dto.UserResponseDto
import com.epam.sp.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
@RequestMapping("/api/v1/users")
class UserController(
        private val userService: UserService
) {

    @PostMapping("/")
    fun createUser(@Validated  @RequestBody userRequestDto: UserRequestDto): ResponseEntity<UserResponseDto> {
        val userResponse = userService.createUser(userRequestDto)
        val link = WebMvcLinkBuilder.linkTo(this::class.java).slash(userResponse.id).toUri()
        return ResponseEntity.created(link).body(userResponse)
    }

    @PutMapping("/")
    fun updateUser(@Validated @RequestBody userRequestDto: UserRequestDto): ResponseEntity<UserResponseDto> {
        val userResponse = userService.updateUser(userRequestDto)
        // PUT can return 200/204 depending on the response. In our case, we return the updated object
        return ResponseEntity.ok(userResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Long> {
        val deletedId = userService.deleteUser(id)
        val link = WebMvcLinkBuilder.linkTo(this::class.java).slash(deletedId).toUri()
        return ResponseEntity.noContent().location(link).build()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserResponseDto> {
        val userResponse = userService.getUser(id) ?: return ResponseEntity.notFound().build()
        val self = WebMvcLinkBuilder.linkTo(this::class.java).slash(id).withSelfRel()
        userResponse.add(self)
        return ResponseEntity.ok(userResponse)
    }

    @Operation(summary = "Get the user list", description = "Return a list of all users in the database")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    ])
    @GetMapping("/")
    fun getAllUsers(): ResponseEntity<List<UserResponseDto?>> {
        val userListResponse = userService.getAllUsers()
        return ResponseEntity.ok(userListResponse)
    }
}