pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        script {
          dockerImage = docker.build("spring-boot-app")
        }
      }
    }

    stage('Test') {
      steps {
        script {
          dockerImage.inside {
            sh 'mvn test'
          }
        }
      }
    }

    stage('Deploy') {
      steps {
        script {
          docker.withRegistry('', 'aws-ecr-credentials') {
            dockerImage.push("latest")
          }
          sshagent(['ec2-instance-ssh']) {
            sh 'ssh -o StrictHostKeyChecking=no ec2-user@<EC2_PUBLIC_IP> "docker run -d -p 8080:8080 spring-boot-app:latest"'
          }
        }
      }
    }
  }
}
