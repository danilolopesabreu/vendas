FROM gcr.io/distroless/base

WORKDIR /app

COPY target/*-runner /app/app

EXPOSE 8080

ENTRYPOINT ["/app/app"]
