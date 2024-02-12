package com.epam.sp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
