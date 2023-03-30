package com.example.plugins

import NewTopicRequest
import SnsService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

val snsService = SnsService()
fun Application.configureRouting() {
    routing {
        post("/topics") {
            val request = call.receive<NewTopicRequest>()

            call.respond(snsService.createSNSTopic(request.name))
        }
    }
}
