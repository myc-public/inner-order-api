pipeline {
    agent {
        kubernetes {
            cloud 'openshift'
            inheritFrom 'java-maven'
            agentContainer 'maven'
            agentInjection true
        }
    }
    options {
        timestamps()
        timeout(time: 10, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build, Test & SonarCloud Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                        mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:5.8.0.7211:sonar \
                            -Dsonar.organization=myc-public \
                            -Dsonar.projectKey=myc-public_inner-order-api
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}