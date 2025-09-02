pipeline {
  agent any
  tools { maven 'Maven3' }
  stages {
    stage('Checkout'){ steps { checkout scm } }
    stage('Build & Deploy (no plugin)'){
      steps {
        withCredentials([usernamePassword(credentialsId: 'jfrog-creds',
                                          usernameVariable: 'ART_USER',
                                          passwordVariable: 'ART_PASS')]) {
          bat """
            mvn -B -U -DskipTests clean package
            mvn -B -U -DskipTests deploy ^
              -DaltDeploymentRepository=artifactory::default::http://%ART_USER%:%ART_PASS%@localhost:8082/artifactory/libs-release-local
          """
          // Si la versión termina en -SNAPSHOT, usa libs-snapshot-local en la URL
        }
      }
    }
    stage('Archive'){ steps { archiveArtifacts artifacts: 'target/*.war', fingerprint: true } }
  }
}
