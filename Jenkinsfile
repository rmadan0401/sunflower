pipeline {
    agent {
        docker {
            image 'androidsdk/android-30:latest'
        }
    }
    environment {
        ANDROID_HOME = '/opt/android-sdk-linux'
        GRADLE_USER_HOME = '.gradle'
    }
    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/rmadan0401/sunflower.git', branch: 'main'
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
