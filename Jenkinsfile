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

          // Resolver por el virtual "maven"
          rtMaven.resolver server: server, releaseRepo: 'maven', snapshotRepo: 'maven'

          // Publicar a locales
          rtMaven.deployer server: server,
                           releaseRepo: 'libs-release-local',
                           snapshotRepo: 'libs-snapshot-local'

          def info = Artifactory.newBuildInfo()
          info.name   = 'mc-login'
          info.number = env.BUILD_NUMBER

          // Al tener el jar-plugin, se generan WAR + JAR y el plugin los deploya
          rtMaven.run pom: 'pom.xml', goals: 'clean package -DskipTests', buildInfo: info

          server.publishBuildInfo(info)
        }
      }
    }

    stage('Archive'){
      steps { archiveArtifacts artifacts: 'target/*.war, target/*.jar', fingerprint: true }
    }
  }
}
