package com.example.plugins

import NewTopicRequest
import SnsService
import com.example.PublishMessageRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val snsService = SnsService()
fun Application.configureRouting() {
    routing {
        post("/topics") {
            val request = call.receive<NewTopicRequest>()

            call.respond(snsService.createSNSTopic(request.name))
        }

        post("/messages") {
            val request = call.receive<PublishMessageRequest>()

            call.respond(snsService.pubTopic(request.topicArn, request.message))
        }
    }
}
