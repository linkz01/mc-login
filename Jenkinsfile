pipeline {
  agent any
  tools { maven 'Maven3' }
  stages {
    stage('Checkout'){ steps { checkout scm } }
    stage('Build & Deploy to Artifactory'){
      steps {
        script {
          def server  = Artifactory.server('jfrog-server')
          def rtMaven = Artifactory.newMavenBuild()
          rtMaven.tool = 'Maven3'
          rtMaven.resolver server: server, releaseRepo: 'maven', snapshotRepo: 'maven'
          rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
          def info = Artifactory.newBuildInfo(); info.name = 'mc-login'; info.number = env.BUILD_NUMBER
          rtMaven.run pom: 'pom.xml', goals: 'clean package -DskipTests', buildInfo: info
          server.publishBuildInfo(info)
        }
      }
    }
    stage('Archive'){ steps { archiveArtifacts artifacts: 'target/*.war', fingerprint: true } }
  }
}
