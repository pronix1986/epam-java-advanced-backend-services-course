package com.epam.sp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class Main

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}