pipeline {
  agent any
  tools { maven 'Maven3' }

  stages {
    stage('Checkout'){ steps { checkout scm } }

    stage('Build & Deploy (sin plugin JFrog)'){
      steps {
        withCredentials([usernamePassword(credentialsId: 'jfrog-creds',
                                          usernameVariable: 'ART_USER',
                                          passwordVariable: 'ART_PASS')]) {
          script {
            // 1) Compilar (genera WAR + JAR con classifier "classes")
            bat 'mvn -B -U -DskipTests clean package'

            // 2) Detectar versión para elegir release vs snapshot
            def version = bat(returnStdout: true,
              script: 'mvn -q -DforceStdout help:evaluate -Dexpression=project.version').trim()

            def repo = version.endsWith('-SNAPSHOT') ? 'libs-snapshot-local' : 'libs-release-local'

            // 3) Publicar a Artifactory (altDeploymentRepository)
            bat """
              mvn -B -U -DskipTests deploy ^
                -DaltDeploymentRepository=artifactory::default::http://%ART_USER%:%ART_PASS%@localhost:8082/artifactory/${repo}
            """
          }
        }
      }
    }

    stage('Archive'){ steps { archiveArtifacts artifacts: 'target/*.war, target/*.jar', fingerprint: true } }
  }
}
