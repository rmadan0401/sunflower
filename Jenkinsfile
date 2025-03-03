pipeline {
    agent any

    environment {
        ANDROID_HOME = '/home/ext_rmadan_vecv_in/android-sdk'
        GRADLE_HOME = '/home/ext_rmadan_vecv_in/gradle-8.2.1'
        PATH = "$ANDROID_HOME/cmdline-tools/latest/bin:$GRADLE_HOME/bin:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Set Up Environment') {
            steps {
                sh '''
                    export ANDROID_HOME=/home/ext_rmadan_vecv_in/android-sdk
                    export GRADLE_HOME=/home/ext_rmadan_vecv_in/gradle-8.2.1
                    export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$GRADLE_HOME/bin:$PATH
                    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses
                    $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.2"
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'gradle assembleDebug'
            }
        }

        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }
    }
}
