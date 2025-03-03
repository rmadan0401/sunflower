pipeline {
    agent any

    environment {
        ANDROID_SDK_ROOT = "/var/lib/jenkins/.android/sdk"
        ANDROID_HOME = "/var/lib/jenkins/.android/sdk"
        PATH = "$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/tools/bin:$ANDROID_SDK_ROOT/build-tools/34.0.0"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install CMDLINE Tools & SDK') {
            steps {
                sh '''
                    echo "Downloading CMDLINE Tools..."
                    wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip
                    echo "Extracting CMDLINE Tools..."
                    tar -xf commandlinetools-linux-10406996_latest.zip
                    mkdir -p $ANDROID_SDK_ROOT/cmdline-tools/latest
                    mv cmdline-tools/* $ANDROID_SDK_ROOT/cmdline-tools/latest/
                    echo "Installing SDK Packages..."
                    yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager "platforms;android-34" "build-tools;34.0.0" || true
                '''
            }
        }

        stage('Accept SDK Licenses') {
            steps {
                sh '''
                    echo "Accepting SDK Licenses..."
                    yes | $ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --licenses || true
                '''
            }
        }

        stage('Build APK') {
            steps {
                sh './gradlew assembleDebug'
            }
        }

        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: '**/*.apk', fingerprint: true
            }
        }
    }
}
