pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/rmadan0401/sunflower.git'
    }

    stages {
        stage('Clone') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: "${GIT_REPO}"]]])
            }
        }
        stage('Build') {
            steps {
                sh './gradlew assembleDebug'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
        }
        stage('Publish APK') {
            steps {
                archiveArtifacts artifacts: '**/app/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }
    }
}
