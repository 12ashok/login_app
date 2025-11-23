pipeline {
  agent any
  environment {
    APP_NAME = 'springboot-login-app'
    IMAGE_NAME = 'your-dockerhub-username/springboot-login-app'
    VERSION = '0.0.1'
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Build & Test') {
      steps {
        sh './mvnw test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }
    stage('Build Image') {
      steps {
        sh "docker build -t ${IMAGE_NAME}:${VERSION} ."
      }
    }
    stage('Push Image') {
      when {
        expression { return env.DOCKER_USER && env.DOCKER_PASS }
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
          sh "docker push ${IMAGE_NAME}:${VERSION}"
        }
      }
    }
    stage('Deploy (Compose)') {
      steps {
        sh 'docker compose up -d --build'
      }
    }
  }
 post {
    always {
      // Publish unit test reports (Surefire)
      junit '**/target/surefire-reports/*.xml'

      // Publish integration test reports (Failsafe), if you use them
      junit '**/target/failsafe-reports/*.xml'
    }
  }
  }
}

