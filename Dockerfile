# ----------------------------------------------------
# 1) Imagem base mínima (não precisa glibc)
#    Distroless "static" é ideal para binários nativos.
# ----------------------------------------------------
FROM ubuntu:22.04

# Diretório de trabalho dentro do container
WORKDIR /app

# Cria usuário não-root
RUN useradd -m nonroot

# Copia o binário nativo gerado pelo GraalVM
COPY target/hostelpro ./hostelpro

RUN chmod +x hostelpro

# Usa o usuário criado
USER nonroot

# Porta exposta
EXPOSE 8080

# Executa o binário nativo
ENTRYPOINT ["/app/hostelpro"]
