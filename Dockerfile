FROM ubuntu:22.04

WORKDIR /app

RUN useradd -m nonroot

COPY target/hostelpro ./hostelpro

RUN chmod +x hostelpro

USER nonroot

EXPOSE 8080

ENTRYPOINT ["./hostelpro"]
