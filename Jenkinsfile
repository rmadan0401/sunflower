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
                    if [ ! -f "${WORKSPACE}/unzip" ]; then
                        wget https://downloads.sourceforge.net/infozip/unzip60.tar.gz -O unzip.tar.gz
                        mkdir -p unzip60
                        tar -xzf unzip.tar.gz -C unzip60
                        chmod +x unzip60/unzip
                        mv unzip60/unzip ${WORKSPACE}/unzip
                    else
                        echo "Unzip Already Installed"
                    fi
                    '''
                }
            }
        }

        stage('Download CMDLINE Tools') {
            steps {
                script {
                    echo "Downloading CMDLINE Tools..."
                    sh '''
                    if [ ! -d "${ANDROID_HOME}/cmdline-tools/latest" ]; then
                        wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O cmdline-tools.zip
                        ${WORKSPACE}/unzip cmdline-tools.zip
                        mkdir -p ${ANDROID_HOME}/cmdline-tools/latest
                        mv cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest
                    else
                        echo "CMDLINE Tools Already Installed"
                    fi
                    '''
                }
            }
        }

        stage('Accept Licenses') {
            steps {
                script {
                    echo "Accepting SDK Licenses..."
                    sh '''
                    yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses || true
                    '''
                }
            }
        }

        stage('Install SDK Packages') {
            steps {
                script {
                    echo "Installing SDK Packages..."
                    sh '''
                    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager "platforms;android-34" "build-tools;34.0.0" "platform-tools"
                    '''
                }
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
