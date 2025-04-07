pipeline {
    agent any

    environment {
        // Set Android SDK and Gradle paths
        ANDROID_HOME = '/home/ext_rmadan_vecv_in/android-sdk'
        PATH = "$ANDROID_HOME/cmdline-tools/latest/bin:$PATH"
    }

    stages {
        // Clean the workspace to ensure a fresh build environment
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        // Checkout the code from the repository
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        // Set up the environment (Android SDK licenses and tools)
        stage('Set Up Environment') {
            steps {
                sh '''
                    # Accept Android SDK licenses
                    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses

                    # Install required Android SDK components
                    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
                '''
            }
        }

        // Make sure gradlew is executable
        stage('Make gradlew Executable') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        // Verify the Gradle version being used
        stage('Check Gradle Version') {
            steps {
                sh "./gradlew --version"
            }
        }

        // Clean build before assembling
        stage('Gradle Clean') {
            steps {
                sh "./gradlew clean"
            }
        }

        // Build the Android project
        stage('Build') {
            steps {
                sh "./gradlew assembleDebug"
            }
        }

        // Run tests
        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }

        // Archive the built APK
        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
