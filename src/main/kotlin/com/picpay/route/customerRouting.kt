package com.picpay.route

import com.picpay.dto.CustomerDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*

fun Route.customerRouting() {

    val customerList = arrayListOf(
        CustomerDTO(1, "Fabio", "123"),
        CustomerDTO(2, "Tadashi", "456"),
        CustomerDTO(3, "Miyasato", "789"),
    )

    route(path = "/customer") {
        get {
            call.respond(customerList)
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
            customerList.add(customer)
            call.respond(status = HttpStatusCode.Created, message = customer)
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
