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

    val customerList = arrayListOf(
        CustomerDTO(1, "Fabio", "123"),
        CustomerDTO(2, "Tadashi", "456"),
        CustomerDTO(3, "Miyasato", "789"),
    )

    route(path = "/customer") {
        get {
            call.respond(service.getAll())
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerList.firstOrNull { it.id == id } ?: return@get call.respondText(
                text = "Not Found",
                status = HttpStatusCode.NotFound
            )
            call.respond(status = HttpStatusCode.OK, message = customer)
        }
        post {
            val customer = call.receive<CustomerDTO>()
            call.respond(status = HttpStatusCode.Created, message = service.create(customer))
        }
        delete("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            customerList.removeIf { it.id == id }
            call.respond(status = HttpStatusCode.NoContent, message = Any())
        }
    }
}
