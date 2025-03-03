pipeline {
    agent any

    environment {
        ANDROID_HOME = '/var/lib/jenkins/.android/sdk'
        PATH = "$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Android SDK + Accept Licenses') {
            steps {
                sh '''
                echo "Downloading CMDLINE Tools..."
                mkdir -p $ANDROID_HOME/cmdline-tools/
                cd $ANDROID_HOME/cmdline-tools/
                wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip

                echo "Extracting CMDLINE Tools..."
                unzip commandlinetools-linux-10406996_latest.zip
                mv cmdline-tools latest

                echo "Accepting SDK Licenses..."
                yes | sdkmanager --licenses || true
                '''
            }
        }

        stage('Gradle Build') {
            steps {
                sh '''
                echo "Building APK..."
                ./gradlew clean assembleDebug --no-daemon
                '''
            }
        }

        stage('Upload APK') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/**/*.apk', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
