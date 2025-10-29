package com.wxgis.topic;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.0)",
    comments = "Source: TiMatrix.OfficialAppSubscribeSvr.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ReadMsgGrpc {

  private ReadMsgGrpc() {}

  public static final String SERVICE_NAME = "TiMatrix.OfficialAppSubscribeSvr.ReadMsg";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<TiMatrixOfficialAppSubscribeSvr.Msg,
      TiMatrixOfficialAppSubscribeSvr.Msg> getReadTopicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReadTopic",
      requestType = TiMatrixOfficialAppSubscribeSvr.Msg.class,
      responseType = TiMatrixOfficialAppSubscribeSvr.Msg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<TiMatrixOfficialAppSubscribeSvr.Msg,
      TiMatrixOfficialAppSubscribeSvr.Msg> getReadTopicMethod() {
    io.grpc.MethodDescriptor<TiMatrixOfficialAppSubscribeSvr.Msg, TiMatrixOfficialAppSubscribeSvr.Msg> getReadTopicMethod;
    if ((getReadTopicMethod = ReadMsgGrpc.getReadTopicMethod) == null) {
      synchronized (ReadMsgGrpc.class) {
        if ((getReadTopicMethod = ReadMsgGrpc.getReadTopicMethod) == null) {
          ReadMsgGrpc.getReadTopicMethod = getReadTopicMethod =
              io.grpc.MethodDescriptor.<TiMatrixOfficialAppSubscribeSvr.Msg, TiMatrixOfficialAppSubscribeSvr.Msg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReadTopic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  TiMatrixOfficialAppSubscribeSvr.Msg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  TiMatrixOfficialAppSubscribeSvr.Msg.getDefaultInstance()))
              .setSchemaDescriptor(new ReadMsgMethodDescriptorSupplier("ReadTopic"))
              .build();
        }
      }
    }
    return getReadTopicMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReadMsgStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReadMsgStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReadMsgStub>() {
        @Override
        public ReadMsgStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReadMsgStub(channel, callOptions);
        }
      };
    return ReadMsgStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReadMsgBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReadMsgBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReadMsgBlockingStub>() {
        @Override
        public ReadMsgBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReadMsgBlockingStub(channel, callOptions);
        }
      };
    return ReadMsgBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReadMsgFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReadMsgFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReadMsgFutureStub>() {
        @Override
        public ReadMsgFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReadMsgFutureStub(channel, callOptions);
        }
      };
    return ReadMsgFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ReadMsgImplBase implements io.grpc.BindableService {

    /**
     */
    public void readTopic(TiMatrixOfficialAppSubscribeSvr.Msg request,
                          io.grpc.stub.StreamObserver<TiMatrixOfficialAppSubscribeSvr.Msg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadTopicMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReadTopicMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                TiMatrixOfficialAppSubscribeSvr.Msg,
                TiMatrixOfficialAppSubscribeSvr.Msg>(
                  this, METHODID_READ_TOPIC)))
          .build();
    }
  }

  /**
   */
  public static final class ReadMsgStub extends io.grpc.stub.AbstractAsyncStub<ReadMsgStub> {
    private ReadMsgStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ReadMsgStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReadMsgStub(channel, callOptions);
    }

    /**
     */
    public void readTopic(TiMatrixOfficialAppSubscribeSvr.Msg request,
                          io.grpc.stub.StreamObserver<TiMatrixOfficialAppSubscribeSvr.Msg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getReadTopicMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ReadMsgBlockingStub extends io.grpc.stub.AbstractBlockingStub<ReadMsgBlockingStub> {
    private ReadMsgBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ReadMsgBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReadMsgBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<TiMatrixOfficialAppSubscribeSvr.Msg> readTopic(
        TiMatrixOfficialAppSubscribeSvr.Msg request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getReadTopicMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ReadMsgFutureStub extends io.grpc.stub.AbstractFutureStub<ReadMsgFutureStub> {
    private ReadMsgFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ReadMsgFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReadMsgFutureStub(channel, callOptions);
    }


    /**
     *
     */
    public  ListenableFuture<TiMatrixOfficialAppSubscribeSvr.Msg> readFutureTopic( TiMatrixOfficialAppSubscribeSvr.Msg request) {
      return ClientCalls.futureUnaryCall(getChannel().newCall(getReadTopicMethod(), getCallOptions()),request);
    }
  }

  private static final int METHODID_READ_TOPIC = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReadMsgImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReadMsgImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_READ_TOPIC:
          serviceImpl.readTopic((TiMatrixOfficialAppSubscribeSvr.Msg) request,
              (io.grpc.stub.StreamObserver<TiMatrixOfficialAppSubscribeSvr.Msg>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ReadMsgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReadMsgBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return TiMatrixOfficialAppSubscribeSvr.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReadMsg");
    }
  }

  private static final class ReadMsgFileDescriptorSupplier
      extends ReadMsgBaseDescriptorSupplier {
    ReadMsgFileDescriptorSupplier() {}
  }

  private static final class ReadMsgMethodDescriptorSupplier
      extends ReadMsgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReadMsgMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ReadMsgGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReadMsgFileDescriptorSupplier())
              .addMethod(getReadTopicMethod())
              .build();
        }
      }
    }
    return result;
  }
}
