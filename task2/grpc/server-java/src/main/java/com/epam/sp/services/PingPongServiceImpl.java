package com.epam.sp.services;

import com.epam.sp.proto.pingpong.PingPongServiceGrpc;
import com.epam.sp.proto.pingpong.Pingpong;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {
    @Override
    public void play(Pingpong.PingRequest request, StreamObserver<Pingpong.PongResponse> responseObserver) {
        final var message = request.getMessage();
        if ("Ping".equals(message)) {
            final var replyStr = "Pong";
            final var reply = Pingpong.PongResponse.newBuilder().setMessage(replyStr).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Only 'Ping' is accepted as an input").asException());
        }
    }
}
