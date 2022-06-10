pipeline {
    agent {
        docker {
            image 'gradle:7.4-jdk17-alpine'
            args '-u root -e GRADLE_USER_HOME=/tmp/.gradle -v /tmp/.gradle:/tmp/.gradle'
        }
    }
    stages {
        stage('Check') {
            steps {
                sh "java -version"
            }
        }
        stage('Clean') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean --no-daemon"
            }
        }
        stage('Test') {
            steps {
                sh "./gradlew test integrationTest --no-daemon"
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Package') {
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
