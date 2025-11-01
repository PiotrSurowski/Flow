# Etap 1: Budowanie gry
FROM gradle:8.9.0-jdk21 AS builder

WORKDIR /app
COPY . .

# Budujemy wersję HTML gry
RUN gradle html:dist --no-daemon

# Etap 2: Serwer statyczny
FROM nginx:alpine

# Skopiuj zbudowane pliki z etapu builda
COPY --from=builder /app/html/build/dist /usr/share/nginx/html

# Otwórz port HTTP
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
