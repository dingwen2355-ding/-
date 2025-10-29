@echo off
protoc.exe --java_out=. --proto_path=. TiMatrix.OfficialAppSubscribeSvr.proto
protoc.exe --plugin=./protoc-gen-grpc-java=./protoc-gen-grpc-java.exe --grpc-java_out=. --proto_path=. TiMatrix.OfficialAppSubscribeSvr.proto