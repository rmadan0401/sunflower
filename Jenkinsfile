pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/rmadan0401/sunflower.git', branch: 'main'
            }
        }

        stage('Setup SDK Path') {
            steps {
                sh '''
                echo "sdk.dir=/var/lib/jenkins/.android/sdk" > local.properties
                mkdir -p /var/lib/jenkins/.android/sdk
                '''
            }
        }

        stage('Accept SDK Licenses') {
            steps {
                sh '''
                yes | ./gradlew --no-daemon sdkmanager --licenses || true
                '''
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

        stage('Publish APK') {
            steps {
                archiveArtifacts artifacts: '**/app/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }
    }
}
