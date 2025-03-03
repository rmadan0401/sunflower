pipeline {
    agent any

    environment {
        ANDROID_HOME = "${WORKSPACE}/android-sdk"
        PATH = "${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/build-tools/34.0.0:${PATH}"
    }

    stages {
        stage('Install Unzip') {
            steps {
                echo "Installing Unzip manually..."
                sh '''
                    if ! command -v unzip >/dev/null 2>&1; then
                        echo "Downloading Unzip..."
                        wget https://downloads.sourceforge.net/infozip/unzip60.tar.gz -O unzip.tar.gz
                        tar -xzf unzip.tar.gz
                        cd unzip60
                        make -f unix/Makefile generic
                        cp unzip ${WORKSPACE}/unzip
                        chmod +x ${WORKSPACE}/unzip
                        echo "Unzip Installed"
                    else
                        echo "Unzip already installed"
                    fi
                '''
            }
        }

        stage('Download Android CMDLINE Tools') {
            steps {
                sh '''
                    echo "Downloading Android CMDLINE Tools..."
                    wget -q https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O cmdline-tools.zip
                    ${WORKSPACE}/unzip cmdline-tools.zip
                    mkdir -p ${ANDROID_HOME}/cmdline-tools/latest
                    mv cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest/
                    echo "CMDLINE Tools Downloaded and Extracted"
                '''
            }
        }

        stage('Install SDK and Accept Licenses') {
            steps {
                sh '''
                    echo "Installing SDK and Accepting Licenses..."
                    mkdir -p ${ANDROID_HOME}/licenses
                    echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > ${ANDROID_HOME}/licenses/android-sdk-license
                    echo "84831b9409646a918e30573bab4c9c91346d8abd" > ${ANDROID_HOME}/licenses/android-sdk-preview-license
                    sdkmanager --sdk_root=${ANDROID_HOME} "platforms;android-34" "build-tools;34.0.0" "platform-tools"
                '''
            }
        }

        stage('Build APK') {
            steps {
                sh '''
                    echo "Building APK..."
                    ./gradlew assembleDebug --no-daemon
                '''
            }
        }

        stage('Archive APK') {
            steps {
                archiveArtifacts artifacts: '**/*.apk', fingerprint: true
            }
        }
    }
}
