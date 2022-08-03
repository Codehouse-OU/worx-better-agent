pipeline {
    agent none
    stages {
        stage('Check') {
            agent {
                docker {
                    image 'gradle:7.4-jdk17-alpine'
                    args '-u root -e GRADLE_USER_HOME=/tmp/.gradle -v /tmp/.gradle:/tmp/.gradle'
                }
            }
            steps {
                sh "java -version"
            }
        }
        stage('Clean') {
            agent {
                docker {
                    image 'gradle:7.4-jdk17-alpine'
                    args '-u root -e GRADLE_USER_HOME=/tmp/.gradle -v /tmp/.gradle:/tmp/.gradle'
                }
            }
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean --no-daemon"
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'gradle:7.4-jdk17-alpine'
                    args '-u root -e GRADLE_USER_HOME=/tmp/.gradle -v /tmp/.gradle:/tmp/.gradle'
                }
            }
            steps {
                sh "./gradlew test integrationTest --no-daemon"
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Create tag') {
            agent {
                docker {
                    image 'jhipster/jhipster:v6.10.4'
                }
            }
            when {
                branch 'master'
            }
            environment {
                GITHUB_CREDS = credentials('github')
            }
            steps {
                sh "chmod +x ./ext/semver.sh"
                sh "git config --global user.email \"markuskarileet@hotmail.com\""
                sh "git config --global credential.helper store"
                sh 'echo "https://$GITHUB_CREDS@github.com" > ~/.git-credentials'
                sh "./ext/semver.sh"
            }
        }
        stage('Package') {
            agent {
                docker {
                    image 'gradle:7.4-jdk17-alpine'
                    args '-u root -e GRADLE_USER_HOME=/tmp/.gradle -v /tmp/.gradle:/tmp/.gradle'
                }
            }
            when {
                anyOf {
                    branch 'master'
                    branch 'development'
                }
            }
            steps {
                sh "./gradlew bootJar -x test --no-daemon"
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }
    }
    post {
        success {
            script {
                if (env.CHANGE_ID) {
                    if (pullRequest.mergeable) {
                        pullRequest.comment('Build successful &#127881;, merging now!')
                        pullRequest.merge(commitTitle: pullRequest.title, mergeMethod: 'rebase')
                        pullRequest.deleteBranch()
                    }
                }
            }
        }
        failure {
            script {
                if (env.CHANGE_ID) {
                    pullRequest.addLabel('blocked')
                }
            }
        }
    }
}
