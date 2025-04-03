pipeline {
    agent any

    environment {
        // Repository configuration
        REPO_URL = 'https://github.com/MyEicher-Export/myeicher-android.git'
        BRANCH = 'export_dev_branch'
        CREDENTIALS_ID = 'github-credentials'
        
        // Android SDK configuration (using your existing SDK)
        ANDROID_HOME = '/home/ext_rmadan_vecv_in/android-sdk'
        // Correct path for cmdline-tools based on your directory structure
        PATH = "$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools:$PATH"
        
        // JDK configuration
        JAVA_HOME = "/var/lib/jenkins/.jdk/jdk-11.0.12"
        PATH = "$JAVA_HOME/bin:$PATH"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                git branch: env.BRANCH,
                    credentialsId: env.CREDENTIALS_ID,
                    url: env.REPO_URL
                
                // Ensure gradlew is executable (handles case where wrapper exists but isn't executable)
                script {
                    if (fileExists('gradlew')) {
                        sh 'chmod +x gradlew'
                    } else {
                        // Create minimal Gradle wrapper if missing
                        sh '''
                            mkdir -p gradle/wrapper
                            echo "distributionBase=GRADLE_USER_HOME" > gradle/wrapper/gradle-wrapper.properties
                            echo "distributionPath=wrapper/dists" >> gradle/wrapper/gradle-wrapper.properties
                            echo "distributionUrl=https\\://services.gradle.org/distributions/gradle-7.4-bin.zip" >> gradle/wrapper/gradle-wrapper.properties
                            curl -L -o gradle/wrapper/gradle-wrapper.jar https://repo.maven.apache.org/maven2/org/gradle/wrapper/gradle-wrapper/7.4/gradle-wrapper-7.4.jar
                            curl -L -o gradlew https://raw.githubusercontent.com/gradle/gradle/master/gradlew
                            chmod +x gradlew
                        '''
                    }
                }
            }
        }

        stage('Set Up Android SDK') {
            steps {
                script {
                    // Verify sdkmanager exists
                    if (!fileExists("$ANDROID_HOME/cmdline-tools/bin/sdkmanager")) {
                        error "sdkmanager not found at $ANDROID_HOME/cmdline-tools/bin/sdkmanager"
                    }
                    
                    // Non-interactive license acceptance
                    sh """
                        mkdir -p $ANDROID_HOME/licenses
                        echo -e "24333f8a63b6825ea9c5514f83c2829b004d1fee\n" > $ANDROID_HOME/licenses/android-sdk-license
                        echo -e "84831b9409646a918e30573bab4c9c91346d8abd\n" > $ANDROID_HOME/licenses/android-sdk-preview-license
                        
                        # Install required components (matches your build.gradle requirements)
                        $ANDROID_HOME/cmdline-tools/bin/sdkmanager --sdk_root=$ANDROID_HOME \
                            "platform-tools" \
                            "platforms;android-33" \
                            "build-tools;30.0.3" < /dev/null
                    """
                }
            }
        }

        stage('Verify Environment') {
            steps {
                sh """
                    echo "=== ENVIRONMENT ==="
                    echo "Java:"
                    $JAVA_HOME/bin/java -version
                    echo "\nAndroid SDK:"
                    ls -la $ANDROID_HOME
                    echo "\nGradle:"
                    ./gradlew --version
                """
            }
        }

        stage('Build') {
            steps {
                sh './gradlew --no-daemon clean assembleDebug'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/outputs/apk/**/*.apk', fingerprint: true
                junit '**/build/test-results/**/*.xml'
            }
        }
    }

    post {
        always {
            sh 'echo "Build finished with status: $BUILD_STATUS"'
            cleanWs()
        }
        failure {
            archiveArtifacts artifacts: '**/build/**/log*.txt', allowEmptyArchive: true
        }
    }
}
