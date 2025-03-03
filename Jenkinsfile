pipeline {
    agent any

    environment {
        ANDROID_SDK_ROOT = "${WORKSPACE}/android-sdk"
        ANDROID_HOME = "${WORKSPACE}/android-sdk"
        CMDLINE_TOOLS = "${ANDROID_SDK_ROOT}/cmdline-tools/latest"
        PATH = "${PATH}:${CMDLINE_TOOLS}/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/tools"
    }

    stages {
        stage('Download Unzip') {
            steps {
                script {
                    if (!fileExists("${WORKSPACE}/unzip")) {
                        echo "Downloading Unzip..."
                        sh '''
                        wget https://github.com/rudix-mac/unzip/raw/master/unzip-6.0.tar.gz -O unzip.tar.gz || wget https://downloads.sourceforge.net/infozip/unzip60.tar.gz -O unzip.tar.gz
                        tar -xzf unzip.tar.gz
                        cd unzip60
                        chmod +x unix/unzipsfx
                        mv unix/unzipsfx ${WORKSPACE}/unzip
                        cd ..
                        '''
                    }
                }
            }
        }

        stage('Download CMDLINE Tools') {
            steps {
                script {
                    if (!fileExists("${CMDLINE_TOOLS}")) {
                        echo "Downloading CMDLINE Tools..."
                        sh '''
                        wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O cmdline-tools.zip
                        ${WORKSPACE}/unzip cmdline-tools.zip
                        mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools/latest
                        mv cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest
                        '''
                    }
                }
            }
        }

        stage('Accept Licenses') {
            steps {
                echo "Accepting SDK Licenses..."
                sh '''
                yes | ${CMDLINE_TOOLS}/bin/sdkmanager --licenses || true
                '''
            }
        }

        stage('Install SDK Packages') {
            steps {
                echo "Installing SDK Packages..."
                sh '''
                ${CMDLINE_TOOLS}/bin/sdkmanager "platforms;android-34" "build-tools;34.0.0" "platform-tools"
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
