pipeline {
    agent any

    environment {
        TOMCAT_URL = 'http://192.168.50.210:8080/'
    }

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                dir('AuthService') { 
                    sh "mvn package -Dmaven.test.skip"
                } 
                dir('UsersService') { 
                    sh "mvn package -Dmaven.test.skip"
                }
                dir('ChatsService') { 
                    sh "mvn package -Dmaven.test.skip"
                }
                dir('UsersManagementService') { 
                    sh "mvn package -Dmaven.test.skip"
                }
                dir('ChatsManagementService') { 
                    sh "mvn package -Dmaven.test.skip"
                }                
            }
        }
        stage('Tests') {
            steps {
                dir('AuthService') { 
                    sh "mvn test"
                }
                dir('UsersService') { 
                    sh "mvn test"
                }
                dir('ChatsService') { 
                    sh "mvn test"
                }
                dir('UsersManagementService') { 
                    sh "mvn test"
                }
                dir('ChatsManagementService') { 
                    sh "mvn test"
                }    
            }
        }

        stage('Deploy') {
            steps {
                dir('AuthService') { 
                    deploy adapters: [tomcat9(url: "${TOMCAT_URL}",
                        credentialsId: 'TomcatCreds')],
                        war: 'target/*.war',
                        contextPath: 'authservice'
                }
                dir('UsersService') { 
                    deploy adapters: [tomcat9(url: "${TOMCAT_URL}",
                        credentialsId: 'TomcatCreds')],
                        war: 'target/*.war',
                        contextPath: 'usersservice'
                }
                dir('ChatsService') { 
                    deploy adapters: [tomcat9(url: "${TOMCAT_URL}",
                        credentialsId: 'TomcatCreds')],
                        war: 'target/*.war',
                        contextPath: 'chatsservice'
                }
                dir('UsersManagementService') { 
                    deploy adapters: [tomcat9(url: "${TOMCAT_URL}",
                        credentialsId: 'TomcatCreds')],
                        war: 'target/*.war',
                        contextPath: 'usersmanagementService'
                }
                dir('ChatsManagementService') { 
                    deploy adapters: [tomcat9(url: "${TOMCAT_URL}",
                        credentialsId: 'TomcatCreds')],
                        war: 'target/*.war',
                        contextPath: 'chatsmanagementservice'
                }    
            }
        }
    }
}
