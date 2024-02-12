package com.epam.sp

import com.epam.sp.proto.pingpong.PingPongServiceGrpc
import com.epam.sp.proto.pingpong.Pingpong
import com.epam.sp.server.GrpcServer
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import kotlin.test.Test
import kotlin.test.assertFailsWith

// Task 2.1.3. Create a simple gRPC client application in any other language you like (or ask a trainer/lector for a Python example)
// that sends a request to the simple server, gets a response, and prints it. Use the same proto file.

// Prerequisites: GrpcServer should be started
class PingPongClientTest {

    @Test
    fun pingPongClient_ShouldGetPongFromServer_WhenPingMessageIsSent() {
        val input = "Ping"
        val channel = ManagedChannelBuilder
            .forAddress("localhost", 8080)
            .usePlaintext()
            .build()

        val stub = PingPongServiceGrpc.newBlockingStub(channel)
        val request = Pingpong.PingRequest.newBuilder().setMessage(input).build()
        val reply = stub.play(request)

        val expected = "Pong"
        println("Got reply: $reply")
        assertEquals(expected, reply.message)

        channel.shutdown()
    }

    @Test
    fun pingPongClient_ShouldGetGetStatusRuntimeException_WhenWrongMessageIsSent() {
        val input = "Dummy"
        val channel = ManagedChannelBuilder
            .forAddress("localhost", 8080)
            .usePlaintext()
            .build()

        val stub = PingPongServiceGrpc.newBlockingStub(channel)
        val request = Pingpong.PingRequest.newBuilder().setMessage(input).build()

        val thrown = assertFailsWith<StatusRuntimeException>(
            message = "Expected to get error",
            block = { stub.play(request) }
        )

        assertEquals(Status.Code.INVALID_ARGUMENT, thrown.status.code)
        assertEquals("Only 'Ping' is accepted as an input", thrown.status.description)
        channel.shutdown()
    }
    companion object {
        private val server = GrpcServer()
        @JvmStatic
        @BeforeAll
        fun setUp() {
            server.startServer()
        }

        @JvmStatic
        @AfterAll
        fun cleanUp() {
            server.stopServer()
        }
    }
}