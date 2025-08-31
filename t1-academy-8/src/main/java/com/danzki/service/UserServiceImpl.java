package com.danzki.service;

import com.danzki.UserName;
import com.danzki.UserResponse;
import com.danzki.UserServiceGrpc;
import com.danzki.model.User;
import com.danzki.repository.UserRepo;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;

@GrpcService
@RequiredArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepo userRepo;

    @Override
    public void add(UserName request,
                    StreamObserver<UserResponse> responseObserver) {
        User user = new User();
        user.setUsername(request.getUsername());
        userRepo.save(user)
                .map(saveUser -> UserResponse.newBuilder()
                        .setId(saveUser.getId() + "")
                        .setUsername(saveUser.getUsername())
                        .build())
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            responseObserver.onError(error);
                        }
                );


    }

    @Override
    public void getAll(Empty request,
                       StreamObserver<UserResponse> responseObserver) {
        Flux.from(userRepo.findAll())
                .map(user ->
                        UserResponse.newBuilder()
                                .setId(user.getId() + "")
                                .setUsername(user.getUsername())
                                .build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                );
    }
}
