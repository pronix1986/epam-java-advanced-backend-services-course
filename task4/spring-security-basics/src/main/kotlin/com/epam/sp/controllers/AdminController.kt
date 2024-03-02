package com.epam.sp.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class AdminController {

    @GetMapping("/admin")
    fun admin() = "admin"

}
