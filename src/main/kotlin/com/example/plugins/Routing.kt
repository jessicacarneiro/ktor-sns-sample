package com.example.plugins

import SnsService
import com.example.requests.NewTopicRequest
import com.example.requests.PublishMessageRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val snsService = SnsService()
fun Application.configureRouting() {
    routing {
        get("/topics") {
            val region = call.request.queryParameters["region"]
            call.respond(snsService.listSNSTopics(region))
        }

        post("/topics") {
            val request = call.receive<NewTopicRequest>()
            call.respond(snsService.createSNSTopic(request.name, request.region))
        }

        delete("/topics/{name}") {
            val topicName = call.parameters["name"]
            val region = call.request.queryParameters["region"]
            call.respond(snsService.deleteSNSTopic(topicName!!, region))
        }

        get("/topics/{name}/attributes") {
            val topicName = call.parameters["name"]
            val region = call.request.queryParameters["region"]
            call.respond(snsService.getSNSTopicAttributes(topicName!!, region))
        }

        get("/subscriptions") {
            val region = call.request.queryParameters["region"]
            call.respond(snsService.listSNSSubscriptions(region))
        }

        post("/messages") {
            val request = call.receive<PublishMessageRequest>()
            call.respond(snsService.pubTopic(request.topicArn, request.message, request.region))
        }
    }
}
