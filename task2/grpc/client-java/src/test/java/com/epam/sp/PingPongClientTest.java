package com.epam.sp;

import com.epam.sp.proto.pingpong.PingPongServiceGrpc;
import com.epam.sp.proto.pingpong.Pingpong;
import com.epam.sp.server.GrpcServer;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Task 2.1.2. Create a simple Java gRPC client application that sends a request to the simple server
// (share the same proto file), gets a response, and prints it.

// Prerequisites: GrpcServer should be started
class PingPongClientTest {
    private static final GrpcServer SERVER = new GrpcServer();
    @BeforeAll
    static void setUp() throws IOException {
        SERVER.startServer();
    }

    @AfterAll
    static void cleanUp() throws InterruptedException {
        SERVER.stopServer();
    }

    @Test
    void pingPongClient_ShouldGetPongFromServer_WhenPingMessageIsSent() {
        final var input = "Ping";
        final var channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        final var stub = PingPongServiceGrpc.newBlockingStub(channel);
        final var request = Pingpong.PingRequest.newBuilder().setMessage(input).build();
        final var reply = stub.play(request);

        final var expected = "Pong";
        System.out.println("Got reply: " + reply);
        assertEquals(expected, reply.getMessage());

        channel.shutdown();
    }

    @Test
    void pingPongClient_ShouldGetStatusRuntimeException_WhenWrongMessageIsSent() {
        final var input = "Dummy";
        final var channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        final var stub = PingPongServiceGrpc.newBlockingStub(channel);
        final var request = Pingpong.PingRequest.newBuilder().setMessage(input).build();

        final var thrown = assertThrows(
                StatusRuntimeException.class,
                () -> stub.play(request),
                "Expected to get error"
        );

        assertEquals(Status.Code.INVALID_ARGUMENT, thrown.getStatus().getCode());
        assertEquals("Only 'Ping' is accepted as an input", thrown.getStatus().getDescription());
        channel.shutdown();
    }

}
