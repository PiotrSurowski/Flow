# Etap 1: Budowa gry (HTML)
FROM gradle:8.9.0-jdk17 AS builder

WORKDIR /app
COPY . .

# Budujemy wersję HTML gry (kompilacja GWT)
RUN gradle html:dist --no-daemon

# Etap 2: Serwer statyczny NGINX
FROM nginx:alpine
COPY --from=builder /app/html/build/dist /usr/share/nginx/html

EXPOSE 8001
CMD ["nginx", "-g", "daemon off;"]
