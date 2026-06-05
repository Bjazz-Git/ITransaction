FROM ubuntu:latest
LABEL authors="Braxt"

ENTRYPOINT ["top", "-b"]