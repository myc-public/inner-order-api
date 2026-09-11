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

        stage('Validate') {
            steps {
                sh 'mvn validate'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                 withSonarQubeEnv('sonarqube') {
                      sh '''
                          mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
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

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}