# ----------------------------------------------------
# 1) Imagem base mínima (não precisa glibc)
#    Distroless "static" é ideal para binários nativos.
# ----------------------------------------------------
FROM gcr.io/distroless/static AS runtime

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o binário nativo gerado pelo GraalVM
COPY target/hostelpro ./hostelpro

# Garante que o binário é executável
USER nonroot:nonroot

# Porta exposta
EXPOSE 8080

# Executa o binário nativo
ENTRYPOINT ["/app/hostelpro"]
