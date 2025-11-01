pipeline {
    agent any

    environment {
        IMAGE_NAME = "flow-game"
        IMAGE_TAG = "latest"
        REGISTRY = "your-dockerhub-username/flow-game" // 🔧 Zmień na swoją nazwę użytkownika DockerHub
        CONTAINER_NAME = "flow-game"
        PORT = "8001"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🚧 Budowanie obrazu Docker..."
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Test Container') {
            steps {
                echo "🧪 Testowanie konfiguracji NGINX w kontenerze..."
                sh 'docker run --rm $IMAGE_NAME:$IMAGE_TAG nginx -t'
            }
        }

        stage('Push to Docker Hub') {
            when {
                branch 'master'
            }
            steps {
                echo "📦 Wysyłanie obrazu do Docker Hub..."
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKERHUB_TOKEN')]) {
                    sh """
                        echo "$DOCKERHUB_TOKEN" | docker login -u your-dockerhub-username --password-stdin
                        docker tag $IMAGE_NAME:$IMAGE_TAG $REGISTRY:$IMAGE_TAG
                        docker push $REGISTRY:$IMAGE_TAG
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "🚀 Uruchamianie kontenera z grą na porcie ${PORT}..."
                sh """
                    docker rm -f $CONTAINER_NAME || true
                    docker run -d --name $CONTAINER_NAME -p $PORT:80 $IMAGE_NAME:$IMAGE_TAG
                """
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline zakończony sukcesem!"
            echo "Gra jest dostępna pod adresem: http://localhost:${PORT} lub http://<adres-serwera>:${PORT}"
        }
        failure {
            echo "❌ Pipeline zakończył się błędem. Sprawdź logi w Jenkinsie."
        }
    }
}
