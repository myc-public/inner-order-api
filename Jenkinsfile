#!/usr/bin/env groovy

final jenkinsProperties = 'cicd/jenkins.properties'
APPLICATION_NAME = ""
APPLICATION_GROUPID = ""
GIT_REPO = ""

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

    parameters {
        choice(name: 'BRANCH', choices: ['develop', 'uat', 'preprod', 'main'], description: 'Git Branch to be used by the build')
    }

    stages {

        stage('Init environment') {
            steps {
                script {
                    def props = readProperties file: jenkinsProperties
                    APPLICATION_NAME    = props["application.name"]?.trim()
                    APPLICATION_GROUPID = props["application.groupId"]?.trim()
                    GIT_REPO            = props["git.url"]?.trim()
                    GIT_BRANCH          = params.BRANCH
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
                script {
                    echo "Repo (jenkins.properties) : ${GIT_REPO}"
                    echo "Branche demandée (paramètre BRANCH) : ${GIT_BRANCH}"

                    def POM = readMavenPom file: 'pom.xml'
                    VERSION = "${POM.version}"
                    echo "Version (pom.xml) : ${VERSION}"

                    isSnapshotVersion = ("${VERSION}" ==~ /(?i)(.*)(-SNAPSHOT)$/)

                    if (params.BRANCH != 'main' && !isSnapshotVersion) {
                        error("RELEASEs should only be built from the main branch, please update the pom's version to be a SNAPSHOT")
                    }
                }
            }
        }

        stage('Build project') {
            steps {
                sh 'mvn --version'
                sh 'mvn clean verify -batch-mode'
            }
        }

        stage('Sonar Code analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.8.0.7211:sonar -batch-mode \
                            -Dsonar.organization=myc-public \
                            -Dsonar.projectKey=myc-public_inner-order-api
                    '''
                }
            }
        }

        stage('Sonar Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Tagging') {
            when {
                expression { params.BRANCH == 'main' }
            }
            steps {
                echo """
                ================================================================
                Tagging de release DESACTIVE pour l'instant.
                Pre-requis avant activation (cf. audit CI/CD Blueprint) :
                  1. Compte de service Git dedie (pas d'identite personnelle)
                  2. Credential Jenkins pour ce compte de service
                  3. Politique de tags immuables (pas de delete + recreate)
                  4. Validation du format de version (x.y.z) avant tag
                  5. Commit retour du pom.xml apres retrait du -SNAPSHOT
                ================================================================
                """
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'clean up our workspace'
            deleteDir()
        }
    }
}
