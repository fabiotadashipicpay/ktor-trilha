package com.picpay

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.picpay.plugins.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureInjection()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
