pipeline {
  agent any
  tools { maven 'Maven3' } // Configura "Maven3" en Manage Jenkins > Global Tool Configuration
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Build')    { steps { bat 'mvn -B -U -DskipTests clean package' } }
    stage('Archive WAR') { steps { archiveArtifacts artifacts: 'target/*.war', fingerprint: true } }
  }
  post { success { echo 'Build OK. WAR archivado.' } }
}
