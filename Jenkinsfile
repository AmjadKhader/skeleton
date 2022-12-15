pipeline {
  agent any

  stages {

    // stage('Setup Environment') {
    //   steps {
    //     catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
    //       sh 'gh repo clone <repo-url>'
    //     }
    //   }
    // }
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }

    stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Install') {

      steps {
        sh 'mvn install -Djava.util.logging.config.file=logging.properties'
      }
    }

    // stage('Deploy') {
    //   steps {
    //     sshagent(credentials: ['<jenkins-credential>']) {
    //       // Copy to server
    //       sh "chmod 400 \"jenkins.pem\""
    //       sh "scp -i jenkins.pem target/skeleton-1.0.0.jar <user>@<aws-ec2-server>:/home/ubuntu"
    //     }
    //   }
    // }

    // stage('Run Service') {
    //   steps {
    //     // sshagent(credentials: ['amj']) {
    //       // // Run/Re-run as server
    //       // sh """ssh -i jenkins.pem -tt <user>@<aws-ec2-server> << EOF
    //       // // Restart the service
    //       // sudo systemctl restart automation-validation
    //       // exit
    //       // EOF"""
    //     // }
    //   }
    // }
  }

  //   post {
  //     always {

  //       // Send email ...
  //       // mail bcc: '', body: "<b>Result</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> Build URL : ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "CI: Project name -> ${env.JOB_NAME}", to: "amjad@amjadkhader.com";
  //     }
  //   }
}