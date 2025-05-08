FROM ubuntu:latest
LABEL authors="arttt"

ENTRYPOINT ["top", "-b"]