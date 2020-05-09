pipeline {
    agent any

    environment {
        registry = "hub.docker.com"
        repository = "springboot-example-monothilic"
        dockerImage = ''
    }
    
    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    docker {
                        dockerImage = docker.build(registry + "/" + repository)
                    }
                }
                sh 'echo "Docker image built"'
            }
        }
    }
}
