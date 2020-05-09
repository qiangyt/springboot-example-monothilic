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

        stage('Image checks') {
            when { branch 'master' }
            steps {
                script {
                    // TODO
                    sh 'docker run --rm ' + registry + '/' + repository + ' java -version'
                    sh 'echo "Image tests passed"'
                    }
                }
            }
        }
    }
}
