pipeline {
    agent any
    environment {
        ANDROID_HOME = "${WORKSPACE}/android-sdk"
        PATH = "${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/tools"
    }

    stages {
        stage('Install Unzip') {
            steps {
                script {
                    echo "Downloading Unzip..."
                    sh '''
                    wget https://downloads.sourceforge.net/infozip/unzip60.tar.gz -O unzip.tar.gz
                    tar -xzf unzip.tar.gz
                    mkdir unzip60
                    tar -xzf unzip.tar.gz -C unzip60
                    cd unzip60
                    chmod +x unix/unzip
                    mv unix/unzip ${WORKSPACE}/unzip
                    '''
                }
            }
        }

        stage('Download CMDLINE Tools') {
            steps {
                script {
                    echo "Downloading CMDLINE Tools..."
                    sh '''
                    wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O cmdline-tools.zip
                    ${WORKSPACE}/unzip cmdline-tools.zip
                    mkdir -p ${ANDROID_HOME}/cmdline-tools/latest
                    mv cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest
                    '''
                }
            }
        }

        stage('Accept Licenses') {
            steps {
                echo "Accepting SDK Licenses..."
                sh '''
                yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses || true
                '''
            }
        }

        stage('Install SDK Packages') {
            steps {
                echo "Installing SDK Packages..."
                sh '''
                ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager "platforms;android-34" "build-tools;34.0.0" "platform-tools"
                '''
            }
        }

        stage('Build App') {
            steps {
                echo "Building App..."
                sh '''
                ./gradlew assembleDebug --no-daemon
                '''
            }
        }
    }
}
