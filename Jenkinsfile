pipeline {
    agent any

    environment {
        PATH = "$WORKSPACE/gradle/wrapper/dists/gradle-*/bin:$PATH" // Gradle wrapper ka path set karna
    }

    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/rmadan0401/sunflower.git', branch: 'main'
            }
        }

        stage('Dependencies') {
            steps {
                sh './gradlew dependencies'
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
