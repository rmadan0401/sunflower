pipeline {
    agent {
        docker {
            image 'reactnativecommunity/react-native-android:latest' // Full Android SDK + React Native SDK
            args '-u root' // Run Docker as root user (Android SDK folder permission fix)
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

        stage('Generate Keystore') {
            steps {
                sh '''
                mkdir -p /root/.android
                if [ ! -f /root/.android/debug.keystore ]; then
                    echo "Generating Debug Keystore 🔑"
                    keytool -genkeypair -v \
                    -keystore /root/.android/debug.keystore \
                    -alias androiddebugkey \
                    -storepass android \
                    -keypass android \
                    -keyalg RSA \
                    -keysize 2048 \
                    -validity 10000 \
                    -dname "CN=Android Debug,O=Android,C=US"
                else
                    echo "✅ Debug Keystore Already Exists"
                fi
                '''
            }
        }

        stage('Dependencies') {
            steps {
                sh './gradlew dependencies'
            }
        }

        stage('Build APK') {
            steps {
                sh '''
                chmod +x ./gradlew
                ./gradlew assembleDebug
                '''
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
