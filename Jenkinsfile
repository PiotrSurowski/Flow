pipeline {
    agent any

    environment {
        IMAGE_NAME = "flow-game"
        IMAGE_TAG = "latest"
        REGISTRY = "your-dockerhub-username/flow-game"  // zmień na swój
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Test') {
            steps {
                sh 'docker run --rm $IMAGE_NAME:$IMAGE_TAG nginx -t'
            }
        }

        stage('Push to Registry') {
            when {
                branch 'main'
            }
            steps {
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
                echo "Deploy your game to server or cloud (manual or automated step)"
                // Możesz tu dodać np. SSH deploy na serwerze lub kubectl apply
            }
        }
    }
}
