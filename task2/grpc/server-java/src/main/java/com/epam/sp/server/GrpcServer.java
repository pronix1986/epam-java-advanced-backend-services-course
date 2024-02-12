package com.epam.sp.server;

import com.epam.sp.services.PingPongServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcServer {
    private Server server;

    public void startServer() throws IOException {
        int port = 8080;
        server = ServerBuilder.forPort(port)
                .addService(new PingPongServiceImpl())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stopServer();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }));
    }

    public void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
            server.shutdownNow();
        }
    }

    public void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        // Task 2.1.1. Create a simple gRPC server application that listens on port 8080

        final var server = new GrpcServer();
        server.startServer();
        server.awaitTermination();

    }
}
