pipeline {
    agent {
        docker {
            image 'markuskarileet/gradle-ci:jdk-17'
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
        stage('Unit Test') {
            steps {
                sh "./gradlew test --no-daemon"
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Integration Test') {
            steps {
                sh "./gradlew integrationTest --no-daemon"
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Sonarqube') {
            steps {
                withSonarQubeEnv('Sonar') {
                    sh "./gradlew jacocoTestReport sonarqube --no-daemon"
                }
            }
        }
        stage('Quality gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Create tag') {
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
