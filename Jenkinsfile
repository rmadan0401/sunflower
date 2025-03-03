pipeline {
    agent {
        docker {
            image 'reactnativecommunity/react-native-android:latest' // Ye pura SDK wala image hai
        }
    }
    environment {
        ANDROID_HOME = '/root/android-sdk'
        PATH = "$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/emulator:$ANDROID_HOME/platform-tools:$PATH"
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
