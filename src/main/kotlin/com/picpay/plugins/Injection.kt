package com.picpay.plugins

import com.picpay.repository.CustomerRepository
import com.picpay.service.CustomerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureInjection() {
    install(Koin) {
        slf4jLogger()
        modules(serviceModule, repositoryModule)
    }
}

val serviceModule = module {
    single { CustomerService(get()) }
}


val repositoryModule = module {
    single { CustomerRepository() }
}
