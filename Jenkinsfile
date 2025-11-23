pipeline {
  agent any
  environment {
    APP_NAME = 'springboot-login-app'
    IMAGE_NAME = 'https://github.com/12ashok/login_app'
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
        sh './mvnw -q -DskipTests package'
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
    success {
      echo 'Build successful.'
    }
    failure {
      echo 'Build failed.'
    }
  }
}
