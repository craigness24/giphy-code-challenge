package com.craig.challenge.giphy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GiphyServiceApplication

fun main(args: Array<String>) {
	runApplication<GiphyServiceApplication>(*args)
}
