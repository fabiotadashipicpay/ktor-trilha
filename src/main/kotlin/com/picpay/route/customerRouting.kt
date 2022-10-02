package com.picpay.route

import com.picpay.dto.CustomerDTO
import com.picpay.service.CustomerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.customerRouting() {
    val service by inject<CustomerService>()

    route(path = "/customer") {
        get {
            call.respond(service.getAll())
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = service.getById(id) ?: return@get call.respondText(
                text = "Not Found",
                status = HttpStatusCode.NotFound
            )
            call.respond(status = HttpStatusCode.OK, message = customer)
        }
        post {
            val customer = call.receive<CustomerDTO>()
            call.respond(status = HttpStatusCode.Created, message = service.create(customer))
        }
        put("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = call.receive<CustomerDTO>()
            val customerDTO = service.update(id = id, customerDTO = customer) ?: return@put call.respondText(
                text = "Bad Request",
                status = HttpStatusCode.BadRequest
            )
            call.respond(status = HttpStatusCode.OK, message = customerDTO)
        }
        delete("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            service.delete(id)
            call.respond(status = HttpStatusCode.NoContent, message = Any())
        }
    }
}
