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
                        wget https://github.com/rudix-mac/unzip/raw/master/unzip-6.0.tar.gz -O unzip.tar.gz
                        tar -xzf unzip.tar.gz
                        chmod +x unzip*/unzip
                        mv unzip*/unzip /usr/bin/unzip || mv unzip*/unzip ${WORKSPACE}/unzip
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
                    mkdir -p ${ANDROID_HOME}/cmdline-tools/latest/
                    mv cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest/
                    echo "CMDLINE Tools Installed"
                '''
            }
        }

        stage('Accept SDK Licenses') {
            steps {
                sh '''
                    echo "Accepting Licenses..."
                    yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses || true
                '''
            }
        }

        stage('Install SDK Packages') {
            steps {
                sh '''
                    echo "Installing Android SDK Packages..."
                    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
                '''
            }
        }

        stage('Build App') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew assembleDebug --no-daemon
                '''
            }
        }
    }

    post {
        always {
            echo "💪 Android Pipeline Completed Boss!"
        }
    }
}
