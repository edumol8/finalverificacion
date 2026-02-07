pipeline {
  agent {
    docker {
      image 'maven:3.9.9-eclipse-temurin-17'
      args '-v $HOME/.m2:/root/.m2'
    }
  }

  stages {
    stage('Clonación del repositorio') {
      steps {
        checkout scm
      }
    }

    stage('Compilación') {
      steps {
        sh 'mvn -B clean compile'
      }
    }

    stage('Pruebas unitarias') {
      steps {
        sh 'mvn -B test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
  }
}
